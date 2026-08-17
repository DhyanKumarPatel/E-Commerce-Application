package com.code4galaxy.e_commerceapp.repository

import com.code4galaxy.e_commerceapp.model.OrderDetailsResponse
import com.code4galaxy.e_commerceapp.model.OrderRequest
import com.code4galaxy.e_commerceapp.model.OrderResponse
import com.code4galaxy.e_commerceapp.network.ApiServices

class OrderRepositoryImpl(private val apiServices: ApiServices): IOrderRepository {
    override suspend fun placeOrder(orderRequest: OrderRequest): OrderResponse {
        return apiServices.placeOrder(orderRequest)
    }

    override suspend fun getOrderDetails(orderId: Int): OrderDetailsResponse {
        return apiServices.getOrderDetails(orderId)
    }

}