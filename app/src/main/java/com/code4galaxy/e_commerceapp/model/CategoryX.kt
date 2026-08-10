package com.code4galaxy.e_commerceapp.model


import com.google.gson.annotations.SerializedName

data class CategoryX(
    @SerializedName("category_id")
    val categoryId: String,
    @SerializedName("category_name")
    val categoryName: String,
    @SerializedName("category_image_url")
    val categoryImageUrl: String,
    @SerializedName("is_active")
    val isActive: String
)