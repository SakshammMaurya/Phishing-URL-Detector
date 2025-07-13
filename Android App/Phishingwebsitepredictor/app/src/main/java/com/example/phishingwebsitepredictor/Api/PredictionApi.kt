package com.example.phishingwebsitepredictor.Api

import com.example.phishingwebsitepredictor.model.PredictionResponse
import com.example.phishingwebsitepredictor.model.UrlRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PredictionApi {
    @POST("predict")
    suspend fun predictUrl(@Body request: UrlRequest): Response<PredictionResponse>
}