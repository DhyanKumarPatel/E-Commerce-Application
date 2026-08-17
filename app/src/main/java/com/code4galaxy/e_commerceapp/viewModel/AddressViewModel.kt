package com.code4galaxy.e_commerceapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code4galaxy.e_commerceapp.model.AddAddressRequest
import com.code4galaxy.e_commerceapp.model.AddAddressResponse
import com.code4galaxy.e_commerceapp.model.AddressResponse
import com.code4galaxy.e_commerceapp.repository.IAddressRepository
import com.code4galaxy.e_commerceapp.utils.UIState
import kotlinx.coroutines.launch

class AddressViewModel(private val repository: IAddressRepository): ViewModel() {
    private val _addressState =
        MutableLiveData<UIState<AddressResponse>>()

    val addressState: LiveData<UIState<AddressResponse>>
        get() = _addressState

    private val _addAddressState =
        MutableLiveData<UIState<AddAddressResponse>>()

    val addAddressState: LiveData<UIState<AddAddressResponse>>
        get() = _addAddressState



    fun addAddress(request: AddAddressRequest) {

        viewModelScope.launch {

            _addAddressState.value = UIState.Loading

            try {

                val response =
                    repository.addAddress(request)

                if (response.status == 0) {

                    _addAddressState.value =
                        UIState.Success(response)

                } else {

                    _addAddressState.value =
                        UIState.Error(response.message)
                }

            } catch (e: Exception) {

                _addAddressState.value =
                    UIState.Error(
                        e.message ?: "Unable to add address"
                    )
            }
        }
    }

    fun getAddresses(userId: String) {

        viewModelScope.launch {

            _addressState.value = UIState.Loading

            try {

                val response =
                    repository.getAddresses(userId)

                if (response.status == 0) {

                    _addressState.value =
                        UIState.Success(response)

                } else {

                    _addressState.value =
                        UIState.Error(response.message)
                }

            } catch (e: Exception) {

                _addressState.value =
                    UIState.Error(
                        e.message ?: "Unable to load addresses"
                    )
            }
        }
    }

}