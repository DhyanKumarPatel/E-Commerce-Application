package com.code4galaxy.e_commerceapp.model


import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("categories")
    val categories: List<CategoryX>
)