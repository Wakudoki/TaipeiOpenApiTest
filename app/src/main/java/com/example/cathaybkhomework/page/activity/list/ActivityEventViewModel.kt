package com.example.cathaybkhomework.page.activity.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cathaybkhomework.common.LoadingState
import com.example.cathaybkhomework.common.LoadingStateImpl
import com.example.cathaybkhomework.data.ActivityEvent
import com.example.cathaybkhomework.repositories.LanguageRepository
import com.example.cathaybkhomework.repositories.TravelApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ActivityEventViewModel(
    private val travelApiRepository: TravelApiRepository,
    private val languageRepository: LanguageRepository
) : ViewModel(), LoadingState by LoadingStateImpl() {

    private val _activityEvent = MutableStateFlow<ActivityEvent?>(null)
    val activityEvent = _activityEvent.asStateFlow()

    init {
        viewModelScope.launch {
            languageRepository.myLanguageKey.collectLatest {
                refresh()
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            fetchActivityEvent()
        }
    }

    private suspend fun fetchActivityEvent() {
        kotlin.runCatching {
            loading()
            travelApiRepository.getActivityEvent()
        }.onSuccess {
            _activityEvent.value = it
            noLoading()
            noRefreshing()
        }.onFailure {
            Log.e("ActivityEventViewModel-fetchActivityEvent", it.message.toString())
            noLoading()
            noRefreshing()
        }
    }
}