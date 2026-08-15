package com.code4galaxy.e_commerceapp.repository

import com.code4galaxy.e_commerceapp.database.CartEntity
import com.code4galaxy.e_commerceapp.model.Product

interface ICartRepository {
    suspend fun getCartItems(userId: String): List<CartEntity>

    suspend fun getCartItem(userId: String, productId: String): CartEntity?

    suspend fun insertCart(cartItem: CartEntity)

    suspend fun updateQuantity(userId: String, productId: String, quantity: Int)

    suspend fun deleteItem(userId: String,productId: String)

    suspend fun clearCart(userId: String)

}