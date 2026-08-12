package com.code4galaxy.e_commerceapp.model

data class SubCategoryResponse(
    val message: String,
    val status: Int,
    val subcategories: List<Subcategory>
)