package com.breaktime.csstaff.api.data

data class Order(
    val endoff: String,
    val orderid: String,
    val payed: String,
    val products: List<Product>,
    val qr: String
)