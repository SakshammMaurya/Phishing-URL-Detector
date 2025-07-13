package com.example.phishingwebsitepredictor.Api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: PredictionApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.1.2:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PredictionApi::class.java)
    }
}