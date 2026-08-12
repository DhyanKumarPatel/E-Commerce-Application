package com.code4galaxy.e_commerceapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code4galaxy.e_commerceapp.model.ProductResponse
import com.code4galaxy.e_commerceapp.repository.IProductRepository
import com.code4galaxy.e_commerceapp.utils.UIState
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: IProductRepository): ViewModel() {
    private val _productState = MutableLiveData<UIState<ProductResponse>>()
    val productState : LiveData<UIState<ProductResponse>>
        get() = _productState


    fun getProducts(subCategoryId : String){
        viewModelScope.launch {
            _productState.value = UIState.Loading

            try {
                val response = repository.getProducts(subCategoryId)

                if (response.message == "Success"){
                    _productState.value = UIState.Success(response)

                } else{
                    _productState.value = UIState.Error(response.message)
                }
            } catch (e: Exception){
                _productState.value = UIState.Error(e.message?:"Something went wrong")
            }
        }
    }
}