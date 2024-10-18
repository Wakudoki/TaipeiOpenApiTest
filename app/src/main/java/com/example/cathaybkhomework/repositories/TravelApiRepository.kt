package com.example.cathaybkhomework.repositories

import com.example.cathaybkhomework.common.MyConst
import com.example.cathaybkhomework.data.Activity
import com.example.cathaybkhomework.data.Attraction
import com.example.cathaybkhomework.data.Audio
import com.example.cathaybkhomework.data.Calendar
import com.example.cathaybkhomework.data.Category
import com.example.cathaybkhomework.data.CategoryType
import com.example.cathaybkhomework.data.News
import com.example.cathaybkhomework.data.Panos
import com.example.cathaybkhomework.data.Tours
import com.example.cathaybkhomework.retrofit.ApiService
import com.example.myandroid.common.language.MyModel


class TravelApiRepository(
    private val apiService: ApiService
) {
    suspend fun getAttractions(): Attraction {
        return apiService.getAttractions()
    }

    suspend fun getEvents(): News {
        return apiService.getNews()
    }

    suspend fun getActivity(): Activity {
        return apiService.getActivity()
    }

    suspend fun getCalendar(): Calendar {
        return apiService.getCalender()
    }

    suspend fun getPanos(): Panos {
        return apiService.getPanos()
    }

    suspend fun getAudio(): Audio {
        return apiService.getAudio()
    }

    suspend fun getCategories(type: CategoryType): Category {
        return apiService.getCategory(MyConst.BASE_URL_GET + "${MyModel.languageKey}/Miscellaneous/Categories?type=${type.name}")
    }

    suspend fun getTours(): Tours {
        return apiService.getTours()
    }
}