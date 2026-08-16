package com.code4galaxy.e_commerceapp.repository

import com.code4galaxy.e_commerceapp.model.RegisterRequest
import com.code4galaxy.e_commerceapp.model.RegisterResponse
import com.code4galaxy.e_commerceapp.network.ApiServices

class RegisterRepositoryImpl(private val apiServices: ApiServices): IRegisterRepository {
    override suspend fun registerUser(request: RegisterRequest): RegisterResponse {
        return apiServices.registerUser(request)
    }

}