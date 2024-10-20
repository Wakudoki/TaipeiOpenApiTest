package com.example.cathaybkhomework.page.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cathaybkhomework.common.LoadingState
import com.example.cathaybkhomework.common.LoadingStateImpl
import com.example.cathaybkhomework.data.Attraction
import com.example.cathaybkhomework.data.News
import com.example.cathaybkhomework.repositories.LanguageRepository
import com.example.cathaybkhomework.repositories.TravelApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    private val travelApiRepository: TravelApiRepository,
    private val languageRepository: LanguageRepository
) : ViewModel(), LoadingState by LoadingStateImpl() {

    private val _attractions = MutableStateFlow<Attraction?>(null)
    val attractions = _attractions.asStateFlow()

    private val _news = MutableStateFlow<News?>(null)
    val news = _news.asStateFlow()

    private val _loadingNewsState = MutableStateFlow(false)
    val loadingNewsState = _loadingNewsState.asStateFlow()

    private val _loadingAttractionState = MutableStateFlow(false)
    val loadingAttractionState = _loadingAttractionState.asStateFlow()

    init {
        viewModelScope.launch {
            languageRepository.myLanguageKey.collectLatest {
                refresh()
            }
        }
    }

    fun refresh() {
        viewModelScope.apply {
            launch { fetchNews() }
            launch { fetchAttractions() }
        }
    }

    private suspend fun fetchNews() {
        kotlin.runCatching {
            _loadingNewsState.value = true
            travelApiRepository.getEvents()
        }.onSuccess {
            _news.value = it
            _loadingNewsState.value = false
            if (!loadingAttractionState.value) {
                noRefreshing()
            }
        }.onFailure {
            Log.e("HomeViewModel-fetchNews", it.message.toString())
            _loadingNewsState.value = false
            if (!loadingAttractionState.value) {
                noRefreshing()
            }
        }
    }

    private suspend fun fetchAttractions() {
        kotlin.runCatching {
            _loadingAttractionState.value = true
            travelApiRepository.getAttractions()
        }.onSuccess {
            _attractions.value = it
            _loadingAttractionState.value = false
            if (!_loadingNewsState.value) {
                noRefreshing()
            }
        }.onFailure {
            Log.e("HomeViewModel-fetchAttractions", it.message.toString())
            _loadingAttractionState.value = false
            if (!_loadingNewsState.value) {
                noRefreshing()
            }
        }
    }
}