package com.example.cathaybkhomework.repositories

import com.example.cathaybkhomework.data.Attraction
import com.example.cathaybkhomework.data.Event
import com.example.cathaybkhomework.retrofit.ApiService


class AttractionsRepository(
    private val apiService: ApiService
) {
    suspend fun getAttractions(): Attraction {
        return apiService.getAttractions()
    }

    suspend fun getEvents(): Event {
        return apiService.getEvents()
    }
}