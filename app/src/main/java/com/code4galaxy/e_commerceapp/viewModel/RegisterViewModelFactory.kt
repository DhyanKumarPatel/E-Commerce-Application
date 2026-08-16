package com.code4galaxy.e_commerceapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.code4galaxy.e_commerceapp.repository.IRegisterRepository

class RegisterViewModelFactory(private val repository: IRegisterRepository): ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)
        ) {

            return RegisterViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }


}
