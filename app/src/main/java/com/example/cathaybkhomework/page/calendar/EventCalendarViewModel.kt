package com.example.cathaybkhomework.page.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cathaybkhomework.common.LoadingState
import com.example.cathaybkhomework.common.LoadingStateImpl
import com.example.cathaybkhomework.repositories.LanguageRepository
import com.example.cathaybkhomework.repositories.TravelApiRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EventCalendarViewModel(
    private val travelApiRepository: TravelApiRepository,
    private val languageRepository: LanguageRepository
) : ViewModel(), LoadingState by LoadingStateImpl() {

    init {
        viewModelScope.launch {
            languageRepository.myLanguageKey.collectLatest {
                refresh()
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {

        }
    }
}