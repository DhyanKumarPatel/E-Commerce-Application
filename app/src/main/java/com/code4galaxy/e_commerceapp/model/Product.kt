package com.code4galaxy.e_commerceapp.model

data class Product(
    val image: Int,
    val name: String,
    val description: String,
    val price: String,
    val rating: Float,
    var quantity: Int = 0
)
