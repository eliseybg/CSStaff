package com.breaktime.csstaff.api.data

data class OrderItem(
    val coffeeshopid: String,
    val orders: List<Order>
)