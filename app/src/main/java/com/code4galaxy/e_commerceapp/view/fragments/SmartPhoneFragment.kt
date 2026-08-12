package com.code4galaxy.e_commerceapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.code4galaxy.e_commerceapp.view.adapters.SmartPhoneViewPagerAdapter
import com.code4galaxy.e_commerceapp.databinding.FragmentSmartphonesBinding
import com.code4galaxy.e_commerceapp.model.Subcategory
import com.code4galaxy.e_commerceapp.network.RetrofitClient
import com.code4galaxy.e_commerceapp.repository.SubCategoryRepositoryImpl
import com.code4galaxy.e_commerceapp.utils.UIState
import com.code4galaxy.e_commerceapp.viewModel.SubCategoryViewModel
import com.code4galaxy.e_commerceapp.viewModel.SubCategoryViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class SmartPhoneFragment: Fragment() {
    private lateinit var binding: FragmentSmartphonesBinding
    private lateinit var viewModel: SubCategoryViewModel
    private lateinit var categoryId: String

    private lateinit var categoryName: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSmartphonesBinding.inflate(inflater, container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCategoryData()

        setUpViewModel()

        setUpObserver()

        setUpBackButton()

        viewModel.subCategory(categoryId)
    }

    private fun setUpBackButton() {
        binding.tvSmartphoneBar
            .setNavigationOnClickListener {

                requireActivity()
                    .onBackPressedDispatcher
                    .onBackPressed()
            }
    }

    private fun setUpObserver() {
        viewModel.SubCategoryState.observe(
            viewLifecycleOwner
        ){state ->
            when(state){
                is UIState.Loading -> {

                    // show progress bar later
                }

                is UIState.Success -> {

                    val subCategories =
                        state.data.subcategories

                    setUpTabs(subCategories)
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

    private fun setUpTabs(subCategories: List<Subcategory>) {
        binding.viewPager.adapter =
            SmartPhoneViewPagerAdapter(
                this,
                subCategories
            )

        TabLayoutMediator(
            binding.tabLayout,
            binding.viewPager
        ) { tab, position ->

            tab.text =
                subCategories[position].subcategory_name

        }.attach()
    }

    private fun setUpViewModel() {
        val repository = SubCategoryRepositoryImpl(RetrofitClient.apiServices)

        val factory = SubCategoryViewModelFactory(repository)

        viewModel = ViewModelProvider(this,factory)[SubCategoryViewModel::class.java]
    }

    private fun getCategoryData() {
        categoryId = arguments?.getString("categoryId") ?: ""

        categoryName = arguments?.getString("categoryName") ?: ""

        binding.tvSmartphoneBar.title = categoryName
    }


}