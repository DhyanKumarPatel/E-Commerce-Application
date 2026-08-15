package com.code4galaxy.e_commerceapp.repository

import com.code4galaxy.e_commerceapp.database.CartDao
import com.code4galaxy.e_commerceapp.database.CartEntity

class CartRepositoryImpl(private val cartDao: CartDao): ICartRepository {
    override suspend fun getCartItems(userId: String): List<CartEntity> {
        return cartDao.getCartItems(userId)
    }

    override suspend fun getCartItem(
        userId: String,
        productId: String
    ): CartEntity? {
        return cartDao.getCartItem(userId,productId)
    }

    override suspend fun insertCart(cartItem: CartEntity) {
        cartDao.insertCartItem(cartItem)
    }

    override suspend fun updateQuantity(
        userId: String,
        productId: String,
        quantity: Int
    ) {
        cartDao.updateQuantity(userId,productId,quantity)
    }

    override suspend fun deleteItem(userId: String, productId: String) {
        cartDao.deleteCartItem(userId,productId)
    }

    override suspend fun clearCart(userId: String) {
        cartDao.clearCart(userId)
    }

}