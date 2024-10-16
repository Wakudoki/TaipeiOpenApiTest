package com.example.cathaybkhomework.retrofit

import com.example.cathaybkhomework.data.Attraction
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {
    @Headers("accept: application/json")
    @GET("Attractions/All")
    suspend fun getAttractions(): Attraction
}