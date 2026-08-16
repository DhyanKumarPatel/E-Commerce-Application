package com.code4galaxy.e_commerceapp.model

data class RegisterRequest(
    val full_name: String,
    val mobile_no: String,
    val email_id: String,
    val password: String
)
