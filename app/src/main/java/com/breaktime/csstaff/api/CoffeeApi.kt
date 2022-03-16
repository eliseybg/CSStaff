package com.breaktime.csstaff.api

import com.breaktime.csstaff.api.data.MenuItem
import com.breaktime.csstaff.api.data.OrderItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CoffeeApi {
    @GET("menu")
    fun getMenu(@Query("id") id: Int = 1): Call<List<MenuItem>>

    @GET("order")
    fun getOrders(@Query("id") id: Int = 1): Call<OrderItem>
}