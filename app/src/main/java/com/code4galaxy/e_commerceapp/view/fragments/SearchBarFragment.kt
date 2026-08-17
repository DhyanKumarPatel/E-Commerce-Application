package com.code4galaxy.e_commerceapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.code4galaxy.e_commerceapp.R
import com.code4galaxy.e_commerceapp.databinding.FragmentSearchBarBinding
import com.code4galaxy.e_commerceapp.network.RetrofitClient
import com.code4galaxy.e_commerceapp.repository.SearchRepositoryImpl
import com.code4galaxy.e_commerceapp.utils.UIState
import com.code4galaxy.e_commerceapp.viewModel.SearchViewModel
import com.code4galaxy.e_commerceapp.viewModel.SearchViewModelFactory


class SearchBarFragment : Fragment() {

    private lateinit var binding: FragmentSearchBarBinding

    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSearchBarBinding.inflate(
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

        setUpViewModel()
        setUpObserver()
        setUpClickListeners()
    }

    private fun setUpViewModel() {

        val repository =
            SearchRepositoryImpl(
                RetrofitClient.apiServices
            )

        val factory =
            SearchViewModelFactory(repository)

        viewModel =
            ViewModelProvider(
                this,
                factory
            )[SearchViewModel::class.java]
    }

    private fun setUpClickListeners() {

        binding.btnClose.setOnClickListener {

            parentFragmentManager
                .beginTransaction()
                .remove(this)
                .commit()
        }

        binding.btnSearch.setOnClickListener {

            val searchText =
                binding.tvSearchBar.text
                    .toString()
                    .trim()

            if (searchText.isEmpty()) {

                Toast.makeText(
                    requireContext(),
                    "Enter product name",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val bundle = Bundle().apply {
                putString("searchText", searchText)
            }

            findNavController().navigate(
                R.id.searchResultFragment,
                bundle
            )
        }
    }

    private fun setUpObserver() {

        viewModel.searchState.observe(
            viewLifecycleOwner
        ) { state ->

            when (state) {

                is UIState.Loading -> {
                    // progress later
                }

                is UIState.Success -> {

                    val products =
                        state.data.products

                    Toast.makeText(
                        requireContext(),
                        "Found ${products.size} products",
                        Toast.LENGTH_SHORT
                    ).show()


                    // display products in RecyclerView
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
}