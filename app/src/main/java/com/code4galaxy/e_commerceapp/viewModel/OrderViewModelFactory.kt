package com.code4galaxy.e_commerceapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.code4galaxy.e_commerceapp.repository.IOrderRepository

class OrderViewModelFactory(
    private val repository: IOrderRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (modelClass.isAssignableFrom(OrderViewModel::class.java)
        ) {

            return OrderViewModel(repository) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class"
        )
    }
}