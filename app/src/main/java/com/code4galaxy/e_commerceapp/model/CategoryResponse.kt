package com.code4galaxy.e_commerceapp.model

data class CategoryResponse(
    val categories: List<Category>,
    val message: String,
    val status: Int
)