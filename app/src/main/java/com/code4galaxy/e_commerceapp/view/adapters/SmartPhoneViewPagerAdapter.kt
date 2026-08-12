package com.code4galaxy.e_commerceapp.view.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.code4galaxy.e_commerceapp.model.Subcategory
import com.code4galaxy.e_commerceapp.view.fragments.ProductListFragment
import com.code4galaxy.e_commerceapp.view.fragments.SmartPhoneFragment


class SmartPhoneViewPagerAdapter( fragment: SmartPhoneFragment, private val subCategories: List<Subcategory>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount() : Int {
        return subCategories.size
    }

    override fun createFragment(position: Int): Fragment {
        val subCategory = subCategories[position]

        return ProductListFragment.newInstance(
            subCategory.subcategory_id
        )
    }
}