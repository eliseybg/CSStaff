package com.breaktime.csstaff.adapter

import com.breaktime.csstaff.api.data.Product

data class ItemData(val userId: String, val qrCode: String, val products: List<Product>)