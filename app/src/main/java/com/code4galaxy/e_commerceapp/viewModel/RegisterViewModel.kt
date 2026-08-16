package com.code4galaxy.e_commerceapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code4galaxy.e_commerceapp.model.RegisterRequest
import com.code4galaxy.e_commerceapp.model.RegisterResponse
import com.code4galaxy.e_commerceapp.repository.IRegisterRepository
import com.code4galaxy.e_commerceapp.utils.UIState
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: IRegisterRepository): ViewModel() {
    private val _registerState = MutableLiveData<UIState<RegisterResponse>>()

    val registerState: LiveData<UIState<RegisterResponse>>
        get() = _registerState

    fun registerUser(request: RegisterRequest) {

        viewModelScope.launch {

            _registerState.value = UIState.Loading

            try {

                val response =
                    repository.registerUser(request)

                if (response.status == 0) {

                    _registerState.value =
                        UIState.Success(response)

                } else {

                    _registerState.value =
                        UIState.Error(response.message)
                }

            } catch (e: Exception) {

                _registerState.value =
                    UIState.Error(
                        e.message ?: "Unable to register user"
                    )
            }

        }

    }
}