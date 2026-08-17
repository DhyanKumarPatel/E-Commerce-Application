package com.code4galaxy.e_commerceapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.code4galaxy.e_commerceapp.database.CartEntity
import com.code4galaxy.e_commerceapp.model.Address

class CheckoutViewModel : ViewModel() {

    var cartItems: List<CartEntity> = emptyList()

    var billAmount: Double = 0.0


    private val _selectedAddress =
        MutableLiveData<Address?>()

    val selectedAddress: LiveData<Address?>
        get() = _selectedAddress


    private val _paymentMethod =
        MutableLiveData<String?>()

    val paymentMethod: LiveData<String?>
        get() = _paymentMethod


    fun setSelectedAddress(address: Address) {
        _selectedAddress.value = address
    }

    fun setPaymentMethod(method: String) {
        _paymentMethod.value = method
    }
}
