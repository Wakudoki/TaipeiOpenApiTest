package com.example.cathaybkhomework.retrofit

import com.example.cathaybkhomework.common.MyConst
import com.example.cathaybkhomework.data.ActivityEvent
import com.example.cathaybkhomework.data.Attraction
import com.example.cathaybkhomework.data.Audio
import com.example.cathaybkhomework.data.Category
import com.example.cathaybkhomework.data.EventCalendar
import com.example.cathaybkhomework.data.News
import com.example.cathaybkhomework.data.Panos
import com.example.cathaybkhomework.data.Tours
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
    suspend fun getNews(@Url url: String = MyConst.BASE_URL_GET + "${MyModel.languageKey}/Events/News"): News

    @Headers("accept: application/json")
    @GET
    suspend fun getActivityEvent(@Url url: String = MyConst.BASE_URL_GET + "${MyModel.languageKey}/Events/Activity"): ActivityEvent

    @Headers("accept: application/json")
    @GET
    suspend fun getEventCalender(@Url url: String = MyConst.BASE_URL_GET + "${MyModel.languageKey}/Events/Calendar"): EventCalendar

    @Headers("accept: application/json")
    @GET
    suspend fun getPanos(@Url url: String = MyConst.BASE_URL_GET + "${MyModel.languageKey}/Media/Panos"): Panos

    @Headers("accept: application/json")
    @GET
    suspend fun getAudio(@Url url: String = MyConst.BASE_URL_GET + "${MyModel.languageKey}/Media/Audio"): Audio

    @Headers("accept: application/json")
    @GET
    suspend fun getCategory(@Url url: String): Category

    @Headers("accept: application/json")
    @GET
    suspend fun getTours(@Url url: String = MyConst.BASE_URL_GET + "${MyModel.languageKey}/Tours/Theme"): Tours
}