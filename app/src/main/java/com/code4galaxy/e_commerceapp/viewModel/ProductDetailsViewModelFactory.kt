package com.code4galaxy.e_commerceapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.code4galaxy.e_commerceapp.repository.IProductDetailsRepository

class ProductDetailsViewModelFactory(private val repository: IProductDetailsRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductDetailsViewModel(repository) as T
    }
}