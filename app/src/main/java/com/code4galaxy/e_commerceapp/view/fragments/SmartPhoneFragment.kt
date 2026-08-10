package com.code4galaxy.e_commerceapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.code4galaxy.e_commerceapp.view.SmartPhoneViewPagerAdapter
import com.code4galaxy.e_commerceapp.databinding.FragmentSmartphonesBinding
import com.google.android.material.tabs.TabLayoutMediator

class SmartPhoneFragment: Fragment() {
    private lateinit var binding: FragmentSmartphonesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSmartphonesBinding.inflate(inflater, container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
    }

    private fun initView() {
        binding.viewPager.adapter = SmartPhoneViewPagerAdapter(this)

        val tabs = listOf("Android", "Iphone", "Windows")

        TabLayoutMediator(binding.tabLayout, binding.viewPager){tab, position ->
            tab.text = tabs[position]
        }.attach()

    }


}