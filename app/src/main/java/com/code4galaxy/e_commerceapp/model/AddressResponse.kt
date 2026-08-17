package com.code4galaxy.e_commerceapp.model

data class AddressResponse(
    val addresses: List<Address>,
    val message: String,
    val status: Int
)