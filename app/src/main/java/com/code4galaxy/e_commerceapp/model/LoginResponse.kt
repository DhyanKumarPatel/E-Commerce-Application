package com.code4galaxy.e_commerceapp.model

data class LoginResponse(
    val status: Int,
    val message: String,
    val user: User?
)