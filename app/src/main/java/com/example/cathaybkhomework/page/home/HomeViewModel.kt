package com.example.cathaybkhomework.page.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cathaybkhomework.data.Attraction
import com.example.cathaybkhomework.data.News
import com.example.cathaybkhomework.repositories.LanguageRepository
import com.example.cathaybkhomework.repositories.TravelApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    private val travelApiRepository: TravelApiRepository,
    private val languageRepository: LanguageRepository
) : ViewModel() {

    private val _attractions = MutableStateFlow<Attraction?>(null)
    val attractions = _attractions.asStateFlow()

    private val _events = MutableStateFlow<News?>(null)
    val events = _events.asStateFlow()


    init {
        viewModelScope.launch(Dispatchers.IO) {
//            fetchEvent()
            languageRepository.myLanguageKey.collectLatest {
                fetchEvent()
            }
        }
    }

    suspend fun fetchEvent() {
        kotlin.runCatching {
            travelApiRepository.getEvents()
        }.onSuccess {
            _events.value = it
        }.onFailure {
            Log.e("HomeViewModel", it.message.toString())
        }
    }
}