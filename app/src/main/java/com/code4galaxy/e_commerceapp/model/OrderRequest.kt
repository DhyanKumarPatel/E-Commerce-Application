package com.code4galaxy.e_commerceapp.model


data class OrderRequest(
    val user_id: String,
    val delivery_address: OrderDeliveryAddress,
    val items: List<OrderItem>,
    val bill_amount: Double,
    val payment_method: String
)