package com.code4galaxy.e_commerceapp.model

data class OrderDetails(
    val order_id: String,
    val address_title: String,
    val address: String,
    val bill_amount: String,
    val payment_method: String,
    val order_status: String,
    val order_date: String,
    val items: List<OrderDetailsItem>
)