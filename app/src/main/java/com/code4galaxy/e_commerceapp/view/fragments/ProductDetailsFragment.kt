package com.code4galaxy.e_commerceapp.view.fragments

import SpecificationAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.code4galaxy.e_commerceapp.database.CartDatabase
import com.code4galaxy.e_commerceapp.database.CartEntity
import com.code4galaxy.e_commerceapp.databinding.FragmentProductDetailsBinding
import com.code4galaxy.e_commerceapp.model.ProductDetails
import com.code4galaxy.e_commerceapp.network.RetrofitClient
import com.code4galaxy.e_commerceapp.repository.CartRepositoryImpl
import com.code4galaxy.e_commerceapp.repository.ProductDetailsRepositoryImpl
import com.code4galaxy.e_commerceapp.utils.UIState
import com.code4galaxy.e_commerceapp.view.adapters.ProductImageAdapter
import com.code4galaxy.e_commerceapp.view.adapters.ReviewAdapter
import com.code4galaxy.e_commerceapp.viewModel.CartViewModel
import com.code4galaxy.e_commerceapp.viewModel.CartViewModelFactory
import com.code4galaxy.e_commerceapp.viewModel.ProductDetailsViewModel
import com.code4galaxy.e_commerceapp.viewModel.ProductDetailsViewModelFactory
import kotlin.String

class ProductDetailsFragment: Fragment() {
    private lateinit var binding: FragmentProductDetailsBinding

    private lateinit var viewModel: ProductDetailsViewModel

    private lateinit var cartViewModel: CartViewModel

    private lateinit var productId: String

    private var currentProduct: ProductDetails? = null

    private val userId = "1"

    private var quantity = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailsBinding.inflate(inflater,container,false)

        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getProductId()
        setUpViewModel()
        setUpObserver()
        setUpRecyclerView()
        setUpCartQuantity()
        setUpCartViewModel()
        setUpCartObserver()

        viewModel.getProductDetails(productId)
        cartViewModel.getCartItem(userId,productId)
    }

    private fun setUpCartObserver() {
        cartViewModel.cartItemState.observe(viewLifecycleOwner){state ->
            when(state){
                is UIState.Success -> {
                    val cartItem = state.data

                    quantity = cartItem?.quantity ?: 0
                    updateUI()

                }

                is UIState.Error -> {
                    Log.e("CART", state.message)
                }

                else -> {}
            }
        }
    }

    private fun setUpCartViewModel() {
        val cartDao = CartDatabase
            .getDatabase(requireContext())
            .cartDao()

        val repository = CartRepositoryImpl(cartDao)

        val factory = CartViewModelFactory(repository)

        cartViewModel = ViewModelProvider(this, factory)[CartViewModel::class.java]
    }


    private fun updateUI() {
        if (quantity == 0) {
            binding.tvAddToCart.visibility = View.VISIBLE

            binding.tvMinus.visibility = View.GONE
            binding.tvQuantity.visibility = View.GONE
            binding.tvPlus.visibility = View.GONE
        } else {
            binding.tvAddToCart.visibility = View.GONE

            binding.tvMinus.visibility = View.VISIBLE
            binding.tvQuantity.visibility = View.VISIBLE
            binding.tvPlus.visibility = View.VISIBLE

            binding.tvQuantity.text = quantity.toString()
        }
    }

    private fun setUpCartQuantity() {



        binding.tvAddToCart.setOnClickListener {

            val product = currentProduct ?: return@setOnClickListener

            quantity = 1

            val cartItem = CartEntity(
                userId = userId,
                productId = product.product_id,
                productName = product.product_name,
                description = product.description,
                imageUrl = product.product_image_url,
                unitPrice = product.price,
                quantity = quantity
            )

            cartViewModel.addToCart(cartItem)

            updateUI()
        }

        binding.tvPlus.setOnClickListener {

            cartViewModel.increaseQuantity(userId,productId,quantity)

            quantity++
            updateUI()
        }

        binding.tvMinus.setOnClickListener {
            cartViewModel.decreaseQuantity(userId,productId,quantity)
            if (quantity > 0) {
                quantity--
            }

            updateUI()
        }

        updateUI()
    }

    private fun setUpRecyclerView() {
        binding.rvSpecifications.layoutManager = LinearLayoutManager(requireContext())

        binding.rvReviews.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setUpObserver() {

        viewModel.productDetailState.observe(
            viewLifecycleOwner
        ) { state ->

            when (state) {

                is UIState.Loading -> {
                    Log.d("PRODUCT_DETAILS", "Loading")
                }

                is UIState.Success -> {

                    val product = state.data.product

                    currentProduct = product

                    binding.tvProductName.text =
                        product.product_name

                    binding.tvDescription.text =
                        product.description

                    binding.tvPrice.text =
                        "$ ${product.price}"

                    binding.ratingBar.rating =
                        product.average_rating.toFloatOrNull() ?: 0f



                    binding.rvSpecifications.adapter = SpecificationAdapter(product.specifications)

                    if (product.reviews.isEmpty()) {

                        binding.rvReviews.visibility = View.GONE
                        binding.tvNoReviews.visibility = View.VISIBLE

                    } else {

                        binding.rvReviews.visibility = View.VISIBLE
                        binding.tvNoReviews.visibility = View.GONE

                        binding.rvReviews.adapter =
                            ReviewAdapter(product.reviews)
                    }


                    binding.viewPagerImages.adapter = ProductImageAdapter(product.images)
                }

                is UIState.Error -> {

                    Log.e(
                        "PRODUCT_DETAILS",
                        "Error: ${state.message}"
                    )
                }
            }
        }
    }

    private fun setUpViewModel() {
        val repository = ProductDetailsRepositoryImpl(RetrofitClient.apiServices)

        val factory = ProductDetailsViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory)[ProductDetailsViewModel::class.java]
    }


    private fun getProductId() {
        productId = arguments?.getString("productId") ?: ""
    }
}