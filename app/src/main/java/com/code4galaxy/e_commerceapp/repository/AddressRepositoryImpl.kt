package com.code4galaxy.e_commerceapp.repository

import com.code4galaxy.e_commerceapp.model.AddAddressRequest
import com.code4galaxy.e_commerceapp.model.AddAddressResponse
import com.code4galaxy.e_commerceapp.model.AddressResponse
import com.code4galaxy.e_commerceapp.network.ApiServices

class AddressRepositoryImpl(private val apiServices: ApiServices): IAddressRepository {
    override suspend fun getAddresses(userId: String): AddressResponse {
        return apiServices.getAddresses(userId)
    }

    override suspend fun addAddress(request: AddAddressRequest): AddAddressResponse {
        return apiServices.addAddress(request)
    }

}