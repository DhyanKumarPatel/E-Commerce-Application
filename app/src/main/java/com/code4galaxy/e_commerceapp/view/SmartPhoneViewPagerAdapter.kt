package com.code4galaxy.e_commerceapp.view

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.code4galaxy.e_commerceapp.view.fragments.AndroidFragment
import com.code4galaxy.e_commerceapp.view.fragments.IphoneFragment
import com.code4galaxy.e_commerceapp.view.fragments.SmartPhoneFragment
import com.code4galaxy.e_commerceapp.view.fragments.WindowsFrament

class SmartPhoneViewPagerAdapter(activity: SmartPhoneFragment): FragmentStateAdapter(activity) {

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> AndroidFragment()
            1 -> IphoneFragment()
            2 -> WindowsFrament()

            else -> AndroidFragment()
        }
    }
}