package com.code4galaxy.e_commerceapp.view.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.code4galaxy.e_commerceapp.view.fragments.CartFragment
import com.code4galaxy.e_commerceapp.view.fragments.CheckoutFragment
import com.code4galaxy.e_commerceapp.view.fragments.OrderFragment

class CheckoutViewPagerAdapter(activity: CheckoutFragment): FragmentStateAdapter(activity) {
    override fun getItemCount() = 4

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> CartFragment()
            1 -> OrderFragment()
            2 -> OrderFragment()
            3 -> OrderFragment()

            else -> CartFragment()
        }
    }


}