package com.example.cathaybkhomework.repositories

import com.example.cathaybkhomework.common.MyConst
import com.example.cathaybkhomework.data.ActivityEvent
import com.example.cathaybkhomework.data.Attraction
import com.example.cathaybkhomework.data.Audio
import com.example.cathaybkhomework.data.EventCalendar
import com.example.cathaybkhomework.data.Category
import com.example.cathaybkhomework.data.CategoryDetail
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

    suspend fun getAttractions(categories: List<CategoryDetail>): Attraction {
        val categoryIds = getCategoryIds(categories)
        return apiService.getAttractions(MyConst.BASE_URL_GET + "${MyModel.languageKey}/Attractions/All$categoryIds")
    }

    suspend fun getEvents(): News {
        return apiService.getNews()
    }

    suspend fun getActivityEvent(): ActivityEvent {
        return apiService.getActivityEvent()
    }

    suspend fun getEventCalendar(): EventCalendar {
        return apiService.getEventCalender()
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

    private fun getCategoryIds(categories: List<CategoryDetail>): String {
        return StringBuilder().apply {
            if (categories.isNotEmpty()) {
                append("?categoryIds=")
                append(categories.joinToString(separator = "%2C") { it.id })
            } else {
                append("")
            }
        }.toString()
    }
}