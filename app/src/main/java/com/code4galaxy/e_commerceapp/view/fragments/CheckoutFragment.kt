package com.code4galaxy.e_commerceapp.view.fragments

import CheckoutPagerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.code4galaxy.e_commerceapp.databinding.FragmentCheckoutBinding
import com.google.android.material.tabs.TabLayoutMediator

class CheckoutFragment : Fragment() {

    private lateinit var binding: FragmentCheckoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCheckoutBinding.inflate(
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

        setUpViewPager()
//        goToNextTab()
    }

    private fun setUpViewPager() {

        binding.viewPager.adapter =
            CheckoutPagerAdapter(this)

        val tabTitles = listOf(
            "Cart Items",
            "Delivery",
            "Payment",
            "Summary"
        )

        TabLayoutMediator(
            binding.tabLayout,
            binding.viewPager
        ) { tab, position ->

            tab.text = tabTitles[position]

        }.attach()
    }

//    fun goToNextTab() {
//        val nextPosition = binding.viewPager.currentItem + 1
//
//        if (nextPosition < 4) {
//            binding.viewPager.currentItem = nextPosition
//        }
//    }
}