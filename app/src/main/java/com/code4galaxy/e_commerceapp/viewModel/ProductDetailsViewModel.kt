package com.code4galaxy.e_commerceapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code4galaxy.e_commerceapp.model.ProductDetailsResponse
import com.code4galaxy.e_commerceapp.repository.IProductDetailsRepository
import com.code4galaxy.e_commerceapp.utils.UIState
import kotlinx.coroutines.launch

class ProductDetailsViewModel(private val repository: IProductDetailsRepository): ViewModel() {

    private val _productDetailsState = MutableLiveData<UIState<ProductDetailsResponse>>()
    val productDetailState : LiveData<UIState<ProductDetailsResponse>>
        get() = _productDetailsState

    fun getProductDetails(productId : String){
        viewModelScope.launch {
            _productDetailsState.value = UIState.Loading

            try {
                val response = repository.getProductDetails(productId)
                if(response.status == 0){
                    _productDetailsState.value = UIState.Success(response)
                } else{
                    _productDetailsState.value = UIState.Error(response.message)
                }
            }catch (e: Exception){
                _productDetailsState.value = UIState.Error(e.message ?: "Something went wrong")
            }


        }
    }
}