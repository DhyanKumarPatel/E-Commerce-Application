package com.code4galaxy.e_commerceapp.model

data class OrderDetailsItem(
    val product_id: String,
    val quantity: String,
    val unit_price: String,
    val amount: String,
    val product_name: String,
    val description: String,
    val product_image_url: String
)