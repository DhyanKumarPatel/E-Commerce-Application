package com.code4galaxy.e_commerceapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.code4galaxy.e_commerceapp.R
import com.code4galaxy.e_commerceapp.databinding.FragmentCheckoutBinding
import com.code4galaxy.e_commerceapp.view.CheckoutViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class CheckoutFragment: Fragment() {

    private lateinit var binding: FragmentCheckoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckoutBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
    }

    private fun initView() {
        binding.viewPager.adapter = CheckoutViewPagerAdapter(this)

        val tabs = listOf("Cart Items", "Delievry", "Payment", "Summary")

        TabLayoutMediator(binding.tabLayout, binding.viewPager){tab, position ->
            tab.text = tabs[position]
        }.attach()
    }
}