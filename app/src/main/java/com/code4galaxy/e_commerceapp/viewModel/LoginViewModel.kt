package com.code4galaxy.e_commerceapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code4galaxy.e_commerceapp.model.LoginRequest
import com.code4galaxy.e_commerceapp.model.LoginResponse
import com.code4galaxy.e_commerceapp.repository.ILoginRepository
import com.code4galaxy.e_commerceapp.utils.UIState
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: ILoginRepository
) : ViewModel() {

    private val _loginState =
        MutableLiveData<UIState<LoginResponse>>()

    val loginState: LiveData<UIState<LoginResponse>>
        get() = _loginState

    fun loginUser(request: LoginRequest) {

        viewModelScope.launch {

            _loginState.value = UIState.Loading

            try {

                val response =
                    repository.loginUser(request)

                if (response.status == 0) {

                    _loginState.value =
                        UIState.Success(response)

                } else {

                    _loginState.value =
                        UIState.Error(response.message)
                }

            } catch (e: Exception) {

                _loginState.value =
                    UIState.Error(
                        e.message ?: "Unable to login"
                    )
            }
        }
    }
}
