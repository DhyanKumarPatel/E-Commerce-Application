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
import com.code4galaxy.e_commerceapp.databinding.FragmentProductListBinding
import com.code4galaxy.e_commerceapp.model.Product
import com.code4galaxy.e_commerceapp.network.RetrofitClient
import com.code4galaxy.e_commerceapp.repository.ProductRepositoryImpl
import com.code4galaxy.e_commerceapp.utils.UIState
import com.code4galaxy.e_commerceapp.view.adapters.ProductAdapter
import com.code4galaxy.e_commerceapp.viewModel.ProductViewModel
import com.code4galaxy.e_commerceapp.viewModel.ProductViewModelFactory
import kotlin.apply

class ProductListFragment: Fragment() {
    private lateinit var  binding: FragmentProductListBinding

    private lateinit var subCategoryId : String

    private lateinit var viewModel: ProductViewModel

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

        getSubCategoryId()

        setUpRecyclerView()

        setUpViewModel()

        setUpObserver()

        viewModel.getProducts(subCategoryId)
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
                    val product = state.data.products

                    binding.rvProducts.adapter = ProductAdapter(product, onProductClick = { product ->
                        val bundle = Bundle().apply{
                            putString("productId", product.product_id)
                        }
                        findNavController().navigate(
                            R.id.productDetailsFragment, bundle
                        )
                    } , onAddToCartClick = { product->
                        Toast.makeText(
                            requireContext(),
                            "${product.product_name} added to cart",
                            Toast.LENGTH_SHORT
                        ).show()
                    })


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