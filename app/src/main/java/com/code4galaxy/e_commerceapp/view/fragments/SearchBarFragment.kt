package com.code4galaxy.e_commerceapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.code4galaxy.e_commerceapp.databinding.FragmentSearchBarBinding

class SearchBarFragment: Fragment() {
    private lateinit var binding: FragmentSearchBarBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBarBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnClose.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .remove(this)
                .commit()
        }
    }
}