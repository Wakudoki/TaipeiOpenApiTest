package com.example.cathaybkhomework.page.calendar

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cathaybkhomework.common.LoadingState
import com.example.cathaybkhomework.common.LoadingStateImpl
import com.example.cathaybkhomework.data.EventCalendar
import com.example.cathaybkhomework.repositories.LanguageRepository
import com.example.cathaybkhomework.repositories.TravelApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EventCalendarViewModel(
    private val travelApiRepository: TravelApiRepository,
    private val languageRepository: LanguageRepository
) : ViewModel(), LoadingState by LoadingStateImpl() {

    private val _eventCalendar = MutableStateFlow<EventCalendar?>(null)
    val eventCalendar = _eventCalendar.asStateFlow()

    init {
        viewModelScope.launch {
            languageRepository.myLanguageKey.collectLatest {
                refresh()
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            fetchEventCalendar()
        }
    }

    private suspend fun fetchEventCalendar() {
        kotlin.runCatching {
            loading()
            travelApiRepository.getEventCalendar()
        }.onSuccess {
            _eventCalendar.value = it.copy(
                data = it.data.sortedByDescending { eventCalendarItem ->
                    eventCalendarItem.begin
                }
            )
            noLoading()
            noRefreshing()
        }.onFailure {
            Log.e("EventCalendarViewModel-fetchEventCalendar", it.message.toString())
            noLoading()
            noRefreshing()
        }
    }
}