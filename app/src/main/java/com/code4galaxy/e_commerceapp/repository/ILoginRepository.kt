package com.code4galaxy.e_commerceapp.repository

import com.code4galaxy.e_commerceapp.model.LoginRequest
import com.code4galaxy.e_commerceapp.model.LoginResponse


interface ILoginRepository {

    suspend fun loginUser(
        request: LoginRequest
    ): LoginResponse
}