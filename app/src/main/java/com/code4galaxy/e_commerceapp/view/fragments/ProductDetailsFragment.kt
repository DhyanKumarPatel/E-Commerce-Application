package com.code4galaxy.e_commerceapp.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.code4galaxy.e_commerceapp.databinding.FragmentProductDetailsBinding
import com.code4galaxy.e_commerceapp.network.RetrofitClient
import com.code4galaxy.e_commerceapp.repository.ProductDetailsRepositoryImpl
import com.code4galaxy.e_commerceapp.utils.UIState
import com.code4galaxy.e_commerceapp.viewModel.ProductDetailsViewModel
import com.code4galaxy.e_commerceapp.viewModel.ProductDetailsViewModelFactory

class ProductDetailsFragment: Fragment() {
    private lateinit var binding: FragmentProductDetailsBinding

    private lateinit var viewModel: ProductDetailsViewModel
    private lateinit var productId: String

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

        viewModel.getProductDetails(productId)
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

                    binding.tvProductName.text =
                        product.product_name

                    binding.tvDescription.text =
                        product.description

                    binding.tvPrice.text =
                        "$ ${product.price}"

                    binding.ratingBar.rating =
                        product.average_rating.toFloatOrNull() ?: 0f

                    val imageUrl =
                        "http://gminnovex.com/myshop/images/${product.product_image_url}"

                    Glide.with(requireContext())
                        .load(imageUrl)
                        .into(binding.ivProduct)
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