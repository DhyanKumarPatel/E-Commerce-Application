package com.code4galaxy.e_commerceapp.repository

import com.code4galaxy.e_commerceapp.model.OrderDetailsResponse
import com.code4galaxy.e_commerceapp.model.OrderRequest
import com.code4galaxy.e_commerceapp.model.OrderResponse

interface IOrderRepository {
    suspend fun placeOrder(
        orderRequest: OrderRequest
    ): OrderResponse

    suspend fun getOrderDetails(
        orderId: Int
    ): OrderDetailsResponse
}