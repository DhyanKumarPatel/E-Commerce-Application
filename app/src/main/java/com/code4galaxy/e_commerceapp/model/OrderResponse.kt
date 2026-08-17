package com.code4galaxy.e_commerceapp.model


data class OrderResponse(
    val status: Int,
    val message: String,
    val order_id: Int?
)