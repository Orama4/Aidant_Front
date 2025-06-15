package com.example.clientaidant.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:3001"
    private const val BASE_URL_ASSISTANCE = "http://10.0.2.2:3002" // service assistance


    val authApiService: AuthApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApiService::class.java)
    }

    val helperApiService: HelperApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_ASSISTANCE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HelperApiService::class.java)
    }

}