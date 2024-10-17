package com.example.cathaybkhomework.retrofit

import com.example.cathaybkhomework.common.MyConst
import com.example.cathaybkhomework.data.Attraction
import com.example.cathaybkhomework.data.Event
import com.example.myandroid.common.language.MyModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Url

interface ApiService {
    @Headers("accept: application/json")
    @GET
    suspend fun getAttractions(@Url url: String = MyConst.BASE_URL_GET + "${MyModel.languageKey}/Attractions/All"): Attraction

    @Headers("accept: application/json")
    @GET
    suspend fun getEvents(@Url url: String = MyConst.BASE_URL_GET + "${MyModel.languageKey}/Events/News"): Event
}