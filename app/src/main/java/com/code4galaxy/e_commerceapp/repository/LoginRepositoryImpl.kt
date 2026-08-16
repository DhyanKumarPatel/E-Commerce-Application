package com.code4galaxy.e_commerceapp.repository

import com.code4galaxy.e_commerceapp.model.LoginRequest
import com.code4galaxy.e_commerceapp.model.LoginResponse
import com.code4galaxy.e_commerceapp.network.ApiServices

class LoginRepositoryImpl(
    private val apiServices: ApiServices
) : ILoginRepository {

    override suspend fun loginUser(
        request: LoginRequest
    ): LoginResponse {

        return apiServices.loginUser(request)
    }
}