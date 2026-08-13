package com.code4galaxy.e_commerceapp.model

data class ProductDetailsResponse(
    val message: String,
    val product: ProductDetails,
    val status: Int
)