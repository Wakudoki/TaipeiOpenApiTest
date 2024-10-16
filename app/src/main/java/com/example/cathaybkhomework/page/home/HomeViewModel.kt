package com.example.cathaybkhomework.page.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cathaybkhomework.data.Attraction
import com.example.cathaybkhomework.repositories.AttractionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val attractionsRepository: AttractionsRepository
): ViewModel() {

    private val _attractions = MutableStateFlow<Attraction?>(null)
    val attractions = _attractions.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                attractionsRepository.getAttractions()
            }.onSuccess {
                _attractions.value = it
            }.onFailure {
                Log.e("HomeViewModel", it.message.toString())
            }
        }
    }
}