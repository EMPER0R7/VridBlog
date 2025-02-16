package com.example.bloger.presentation

import com.example.bloger.data.BlogApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://blog.vrid.in/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: BlogApi = retrofit.create(BlogApi::class.java)
}