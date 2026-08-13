package com.code4galaxy.e_commerceapp.model

data class ProductReview(
    val user_id: String,
    val full_name: String,
    val review_id: String,
    val review_title: String,
    val review: String,
    val rating: String,
    val review_date: String
)
