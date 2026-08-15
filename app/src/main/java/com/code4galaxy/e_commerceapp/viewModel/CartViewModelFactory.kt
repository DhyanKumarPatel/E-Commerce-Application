package com.code4galaxy.e_commerceapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.code4galaxy.e_commerceapp.repository.ICartRepository

class CartViewModelFactory(private val repository: ICartRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown View Model Class")
    }
}