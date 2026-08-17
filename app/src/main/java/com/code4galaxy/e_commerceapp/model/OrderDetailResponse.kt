package com.code4galaxy.e_commerceapp.model

data class OrderDetailsResponse(
    val status: Int,
    val message: String,
    val order: OrderDetails
)