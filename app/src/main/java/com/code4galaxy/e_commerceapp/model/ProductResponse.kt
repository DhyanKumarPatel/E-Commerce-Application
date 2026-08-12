package com.code4galaxy.e_commerceapp.model

data class ProductResponse(
    val message: String,
    val products: List<Product>,
    val status: Int
)