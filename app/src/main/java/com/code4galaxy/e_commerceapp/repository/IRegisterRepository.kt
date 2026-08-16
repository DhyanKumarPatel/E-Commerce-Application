package com.code4galaxy.e_commerceapp.repository

import com.code4galaxy.e_commerceapp.model.RegisterRequest
import com.code4galaxy.e_commerceapp.model.RegisterResponse

interface IRegisterRepository {
    suspend fun registerUser(
        request: RegisterRequest
    ): RegisterResponse
}