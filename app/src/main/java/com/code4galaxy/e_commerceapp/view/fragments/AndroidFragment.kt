package com.code4galaxy.e_commerceapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.code4galaxy.e_commerceapp.model.Product
import com.code4galaxy.e_commerceapp.view.ProductAdapter
import com.code4galaxy.e_commerceapp.R
import com.code4galaxy.e_commerceapp.databinding.FragmentAndroidBinding

class AndroidFragment: Fragment() {
    private lateinit var binding: FragmentAndroidBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAndroidBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val products = listOf(
            Product(
                image = R.drawable.ic_launcher_foreground,
                name = "RealMe Nazro 50",
                description = "realme narzo 50 (Speed Blue, 4GB RAM+64GB Storage) Helio G96 Processor | 50MP AI Triple Camera",
                price = "$ 200",
                rating = 4.5f
            ),
            Product(
                image = R.drawable.ic_launcher_foreground,
                name = "Redmi Note 11T",
                description = "Redmi Note 11T 5G (Matte Black 6GB RAM 128GB ROM) Dimensity 810 5G",
                price = "$ 220",
                rating = 4.0f
            ),
            Product(
                image = R.drawable.ic_launcher_foreground,
                name = "Xiaomi 11 Lite NE 5G",
                description = "Xiaomi 11 Lite NE 5G Vinyl Black 6GB RAM 128GB Storage",
                price = "$ 300",
                rating = 4.5f
            ),
            Product(
                image = R.drawable.ic_launcher_foreground,
                name = "Redmi 9A",
                description = "Redmi 9A Midnight Black 3GB RAM with long-lasting battery",
                price = "$ 150",
                rating = 4.0f
            )
        )

        binding.rvAndoid.apply {
            adapter = ProductAdapter(products)
        }
    }
}