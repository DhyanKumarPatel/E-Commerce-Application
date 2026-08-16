package com.code4galaxy.e_commerceapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.code4galaxy.e_commerceapp.R
import com.code4galaxy.e_commerceapp.database.CartDao
import com.code4galaxy.e_commerceapp.database.CartDatabase
import com.code4galaxy.e_commerceapp.database.CartEntity
import com.code4galaxy.e_commerceapp.databinding.FragmentProductListBinding
import com.code4galaxy.e_commerceapp.model.Product
import com.code4galaxy.e_commerceapp.network.RetrofitClient
import com.code4galaxy.e_commerceapp.repository.CartRepositoryImpl
import com.code4galaxy.e_commerceapp.repository.ProductRepositoryImpl
import com.code4galaxy.e_commerceapp.utils.SessionManager
import com.code4galaxy.e_commerceapp.utils.UIState
import com.code4galaxy.e_commerceapp.view.adapters.ProductAdapter
import com.code4galaxy.e_commerceapp.viewModel.CartViewModel
import com.code4galaxy.e_commerceapp.viewModel.CartViewModelFactory
import com.code4galaxy.e_commerceapp.viewModel.ProductViewModel
import com.code4galaxy.e_commerceapp.viewModel.ProductViewModelFactory
import kotlin.apply

class ProductListFragment: Fragment() {
    private lateinit var  binding: FragmentProductListBinding

    private lateinit var subCategoryId : String

    private lateinit var viewModel: ProductViewModel

    private lateinit var cartViewModel: CartViewModel

    private lateinit var sessionManager: SessionManager
    private lateinit var userId: String

    private var products: List<Product> = emptyList()

    private var cartItems: List<CartEntity> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductListBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())
        userId = sessionManager.getUserId()

        getSubCategoryId()

        setUpRecyclerView()

        setUpViewModel()

        setUpObserver()

        setUpCartViewModel()

        setUpCartObserver()

        viewModel.getProducts(subCategoryId)
        cartViewModel.getCartItems(userId)
    }

    private fun setUpCartObserver() {

        cartViewModel.cartState.observe(viewLifecycleOwner) { state ->

            when (state) {

                is UIState.Success -> {
                    cartItems = state.data

                    updateProductAdapter()
                }

                is UIState.Error -> {

                    Toast.makeText(
                        requireContext(),
                        state.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {}
            }
        }
    }

    private fun updateProductAdapter() {
        binding.rvProducts.adapter = ProductAdapter(products, cartItems, onProductClick = { product ->
            val bundle = Bundle().apply{
                putString("productId", product.product_id)
            }
            findNavController().navigate(
                R.id.productDetailsFragment, bundle
            )
        } , onAddToCartClick = { product->
            val cartItem = CartEntity(
                userId = userId,
                productId = product.product_id,
                productName = product.product_name,
                description = product.description,
                imageUrl = product.product_image_url,
                unitPrice = product.price,
                quantity = 1
            )

            cartViewModel.addToCart(cartItem)
        },
            onPlusClick = {product, quantity ->
                cartViewModel.increaseQuantity(userId,product.product_id,quantity)
            },

            onMinusClick = {product, quantity ->
                cartViewModel.decreaseQuantity(userId,product.product_id,quantity)
            }

            )
    }

    private fun setUpCartViewModel() {
        val cartDao = CartDatabase.getDatabase(requireContext())
            .cartDao()

        val repository = CartRepositoryImpl(cartDao)
        val factory = CartViewModelFactory(repository)

        cartViewModel = ViewModelProvider(this, factory)[CartViewModel::class.java]
    }

    private fun setUpObserver() {
        viewModel.productState.observe(
            viewLifecycleOwner
        ){state ->
            when(state){
                is UIState.Loading -> {
                    //progress bar
                }
                is UIState.Success -> {
                    products = state.data.products

                    updateProductAdapter()
                }

                is UIState.Error -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setUpViewModel() {
        val repository = ProductRepositoryImpl(RetrofitClient.apiServices)

        val factory = ProductViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory)[ProductViewModel::class.java]
    }

    private fun setUpRecyclerView() {
        binding.rvProducts.layoutManager =
            LinearLayoutManager(requireContext())
    }

    private fun getSubCategoryId() {
         subCategoryId = arguments?.getString("subCategoryId") ?: ""
    }


    companion object {

        fun newInstance(
            subCategoryId: String
        ): ProductListFragment {

            val fragment = ProductListFragment()

            val bundle = Bundle()

            bundle.putString(
                "subCategoryId",
                subCategoryId
            )

            fragment.arguments = bundle

            return fragment
        }
    }
}