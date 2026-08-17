package com.code4galaxy.e_commerceapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.code4galaxy.e_commerceapp.R
import com.code4galaxy.e_commerceapp.database.CartDatabase
import com.code4galaxy.e_commerceapp.database.CartEntity
import com.code4galaxy.e_commerceapp.databinding.FragmentSearchResultBinding
import com.code4galaxy.e_commerceapp.model.Product
import com.code4galaxy.e_commerceapp.network.RetrofitClient
import com.code4galaxy.e_commerceapp.repository.CartRepositoryImpl
import com.code4galaxy.e_commerceapp.repository.SearchRepositoryImpl
import com.code4galaxy.e_commerceapp.utils.SessionManager
import com.code4galaxy.e_commerceapp.utils.UIState
import com.code4galaxy.e_commerceapp.view.adapters.ProductAdapter
import com.code4galaxy.e_commerceapp.viewModel.CartViewModel
import com.code4galaxy.e_commerceapp.viewModel.CartViewModelFactory
import com.code4galaxy.e_commerceapp.viewModel.SearchViewModel
import com.code4galaxy.e_commerceapp.viewModel.SearchViewModelFactory

class SearchResultFragment : Fragment() {

    private lateinit var binding: FragmentSearchResultBinding

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var cartViewModel: CartViewModel

    private lateinit var searchText: String
    private lateinit var userId: String

    private var products: List<Product> = emptyList()
    private var cartItems: List<CartEntity> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSearchResultBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        searchText =
            arguments?.getString("searchText") ?: ""

        userId =
            SessionManager(requireContext()).getUserId()

        setUpRecyclerView()

        setUpSearchViewModel()
        setUpCartViewModel()

        setUpSearchObserver()
        setUpCartObserver()

        searchViewModel.searchProducts(searchText)

        cartViewModel.getCartItems(userId)
    }

    private fun setUpRecyclerView() {

        binding.rvSearchProducts.layoutManager =
            LinearLayoutManager(requireContext())
    }

    private fun setUpSearchViewModel() {

        val repository =
            SearchRepositoryImpl(
                RetrofitClient.apiServices
            )

        val factory =
            SearchViewModelFactory(repository)

        searchViewModel =
            ViewModelProvider(
                this,
                factory
            )[SearchViewModel::class.java]
    }

    private fun setUpCartViewModel() {

        val cartDao =
            CartDatabase
                .getDatabase(requireContext())
                .cartDao()

        val repository =
            CartRepositoryImpl(cartDao)

        val factory =
            CartViewModelFactory(repository)

        cartViewModel =
            ViewModelProvider(
                this,
                factory
            )[CartViewModel::class.java]
    }

    private fun setUpSearchObserver() {

        searchViewModel.searchState.observe(
            viewLifecycleOwner
        ) { state ->

            when (state) {

                is UIState.Loading -> {
                    // Progress bar later
                }

                is UIState.Success -> {

                    products = state.data.products

                    if (products.isEmpty()) {

                        binding.rvSearchProducts.visibility =
                            View.GONE

                        binding.tvNoProducts.visibility =
                            View.VISIBLE

                    } else {

                        binding.rvSearchProducts.visibility =
                            View.VISIBLE

                        binding.tvNoProducts.visibility =
                            View.GONE
                    }

                    updateProductAdapter()
                }

                is UIState.Error -> {

                    Toast.makeText(
                        requireContext(),
                        state.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setUpCartObserver() {

        cartViewModel.cartState.observe(
            viewLifecycleOwner
        ) { state ->

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

        binding.rvSearchProducts.adapter =
            ProductAdapter(

                products,

                cartItems,

                onProductClick = { product ->

                    val bundle = Bundle().apply {

                        putString(
                            "productId",
                            product.product_id
                        )
                    }

                    findNavController().navigate(
                        R.id.productDetailsFragment,
                        bundle
                    )
                },

                onAddToCartClick = { product ->

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

                onPlusClick = { product, quantity ->

                    cartViewModel.increaseQuantity(
                        userId,
                        product.product_id,
                        quantity
                    )
                },

                onMinusClick = { product, quantity ->

                    cartViewModel.decreaseQuantity(
                        userId,
                        product.product_id,
                        quantity
                    )
                }
            )
    }
}