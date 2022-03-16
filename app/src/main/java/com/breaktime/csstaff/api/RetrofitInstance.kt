package com.breaktime.csstaff.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://coffeetogo.madskill.ru/"

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val API: CoffeeApi by lazy {
        retrofit.create(CoffeeApi::class.java)
    }
}