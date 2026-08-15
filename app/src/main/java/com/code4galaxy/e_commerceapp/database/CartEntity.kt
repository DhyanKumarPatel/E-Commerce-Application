package com.code4galaxy.e_commerceapp.database

import androidx.room.Entity

@Entity(
    tableName = "cart",
    primaryKeys = ["userId", "productId"]
)
data class CartEntity(

    val userId: String,

    val productId: String,

    val productName: String,

    val description: String,

    val imageUrl: String,

    val unitPrice: String,

    val quantity: Int
)