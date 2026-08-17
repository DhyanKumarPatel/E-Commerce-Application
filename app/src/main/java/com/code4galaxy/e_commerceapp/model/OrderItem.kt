package com.code4galaxy.e_commerceapp.model

data class OrderItem(
    val product_id: String,
    val quantity: Int,
    val unit_price: Double
)