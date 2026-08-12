package com.code4galaxy.e_commerceapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.code4galaxy.e_commerceapp.R
import com.code4galaxy.e_commerceapp.databinding.FragmentHomeBinding
import com.code4galaxy.e_commerceapp.network.RetrofitClient
import com.code4galaxy.e_commerceapp.repository.CategoryRepositoryImpl
import com.code4galaxy.e_commerceapp.utils.UIState
import com.code4galaxy.e_commerceapp.view.adapters.CategoryAdapter
import com.code4galaxy.e_commerceapp.viewModel.CategoryViewModel
import com.code4galaxy.e_commerceapp.viewModel.CategoryViewModelFactory

class HomeFragment: Fragment() {

private lateinit var binding: FragmentHomeBinding

private lateinit var viewModel: CategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        setUpViewModel()
        setUpObserver()
        viewModel.getCategories()
    }

    private fun setUpObserver() {
        viewModel.categoryState.observe(viewLifecycleOwner){state ->
            when(state){
                is UIState.Loading -> {

                    //  progress bar
                }

                is UIState.Success-> {

                    val response = state.data

                    binding.rvCategories.adapter =
                        CategoryAdapter(response.categories) { category ->

                            val bundle = Bundle().apply {

                                putString(
                                    "categoryId",
                                    category.category_id
                                )

                                putString(
                                    "categoryName",
                                    category.category_name
                                )
                            }

                            findNavController().navigate(
                                R.id.smartPhoneFragment, bundle
                            )

                        }
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

    private fun setUpViewModel() {
        val repository = CategoryRepositoryImpl(RetrofitClient.apiServices)

        val factory = CategoryViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory)[CategoryViewModel::class.java]
    }


    private fun setUpRecyclerView() {

        binding.rvCategories.layoutManager = GridLayoutManager(requireContext(), 2)

    }


}