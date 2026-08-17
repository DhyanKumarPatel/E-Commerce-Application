package com.code4galaxy.e_commerceapp.repository

import com.code4galaxy.e_commerceapp.model.AddAddressRequest
import com.code4galaxy.e_commerceapp.model.AddAddressResponse
import com.code4galaxy.e_commerceapp.model.AddressResponse

interface IAddressRepository {
   suspend fun getAddresses(userId: String): AddressResponse

    suspend fun addAddress(request: AddAddressRequest): AddAddressResponse
}