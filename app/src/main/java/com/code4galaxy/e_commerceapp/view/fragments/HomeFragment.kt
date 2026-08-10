package com.code4galaxy.e_commerceapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.code4galaxy.e_commerceapp.model.Category
import com.code4galaxy.e_commerceapp.R
import com.code4galaxy.e_commerceapp.databinding.FragmentHomeBinding

class HomeFragment: Fragment() {

private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpRecyclerView()
    }



    private fun setUpRecyclerView() {
        val categories = listOf(
            Category(
                image = R.drawable.smartpone,
                name = "Smart Phones"
            ),
            Category(
                image = R.drawable.smartpone,
                name = "Laptops"
            ),
            Category(
                image = R.drawable.smartpone,
                name = "Mens Wear"
            ),
            Category(
                image = R.drawable.smartpone,
                name = "Women’s Wear"
            ),
            Category(
                image = R.drawable.smartpone,
                name = "Kids Wear"
            ),
            Category(
                image = R.drawable.smartpone,
                name = "Grocery"
            )
        )

        binding.rvCategories.layoutManager = GridLayoutManager(requireContext(), 2)

        binding.rvCategories.adapter = CategoryAdapter(categories) { category ->

            when (category.name) {
                "Smart Phones" -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, SmartPhoneFragment())
                        .addToBackStack(null)
                        .commit()
                }
            }
        }


    }


}