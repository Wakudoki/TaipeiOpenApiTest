package com.example.cathaybkhomework.page.tours.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cathaybkhomework.common.LoadingState
import com.example.cathaybkhomework.common.LoadingStateImpl
import com.example.cathaybkhomework.data.Tours
import com.example.cathaybkhomework.repositories.LanguageRepository
import com.example.cathaybkhomework.repositories.TravelApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ToursViewModel(
    private val travelApiRepository: TravelApiRepository,
    private val languageRepository: LanguageRepository
) : ViewModel(), LoadingState by LoadingStateImpl() {

    private val _tours = MutableStateFlow<Tours?>(null)
    val tours = _tours.asStateFlow()

    init {
        viewModelScope.launch {
            languageRepository.myLanguageKey.collectLatest {
                refresh()
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            fetchTours()
        }
    }

    private suspend fun fetchTours() {
        kotlin.runCatching {
            loading()
            _tours.value = null
            travelApiRepository.getTours()
        }.onSuccess {
            _tours.value = it
            noLoading()
            noRefreshing()
        }.onFailure {
            Log.e("ToursViewModel-fetchTours", it.message.toString())
            noLoading()
            noRefreshing()
        }
    }
}