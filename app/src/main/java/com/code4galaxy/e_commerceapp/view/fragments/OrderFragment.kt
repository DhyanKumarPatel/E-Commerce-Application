package com.code4galaxy.e_commerceapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.code4galaxy.e_commerceapp.databinding.FragmentOrdersBinding

class OrderFragment: Fragment() {

    private lateinit var binding: FragmentOrdersBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOrdersBinding.inflate(inflater,container,false)

        return binding.root
    }
}