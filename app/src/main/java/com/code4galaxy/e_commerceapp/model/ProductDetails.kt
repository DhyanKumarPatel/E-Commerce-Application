package com.code4galaxy.e_commerceapp.model

data class ProductDetails(
    val average_rating: String,
    val category_id: String,
    val description: String,
    val images: List<ProductImage>,
    val is_active: String,
    val price: String,
    val product_id: String,
    val product_image_url: String,
    val product_name: String,
    val reviews: List<Any>,
    val specifications: List<ProductSpecification>,
    val sub_category_id: String
)