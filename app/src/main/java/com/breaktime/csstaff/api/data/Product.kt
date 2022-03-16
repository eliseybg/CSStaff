package com.breaktime.csstaff.api.data

data class Product(
    val coffee: String,
    val count: String,
    val milk: String,
    val productid: String,
    val sugar: String,
    val syrup: String,
    val volume: String
) {
    var img: String = ""
    var price: String = ""
}