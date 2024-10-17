package com.example.cathaybkhomework.page.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cathaybkhomework.data.Attraction
import com.example.cathaybkhomework.data.Event
import com.example.cathaybkhomework.repositories.AttractionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val attractionsRepository: AttractionsRepository
) : ViewModel() {

    private val _attractions = MutableStateFlow<Attraction?>(null)
    val attractions = _attractions.asStateFlow()

    private val _events = MutableStateFlow<Event?>(null)
    val events = _events.asStateFlow()


    init {
        viewModelScope.launch(Dispatchers.IO) {
            fetchEvent()
        }
    }

    suspend fun fetchEvent() {
        kotlin.runCatching {
            attractionsRepository.getEvents()
        }.onSuccess {
            _events.value = it
        }.onFailure {
            Log.e("HomeViewModel", it.message.toString())
        }
    }


}