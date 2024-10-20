package com.example.cathaybkhomework.page.attraction

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cathaybkhomework.common.LoadingState
import com.example.cathaybkhomework.common.LoadingStateImpl
import com.example.cathaybkhomework.data.Attraction
import com.example.cathaybkhomework.data.Category
import com.example.cathaybkhomework.data.CategoryDetail
import com.example.cathaybkhomework.data.CategoryType
import com.example.cathaybkhomework.repositories.LanguageRepository
import com.example.cathaybkhomework.repositories.TravelApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AttractionViewModel(
    private val travelApiRepository: TravelApiRepository,
    private val languageRepository: LanguageRepository
) : ViewModel(), LoadingState by LoadingStateImpl() {
    private val _attractions = MutableStateFlow<Attraction?>(null)
    val attractions = _attractions.asStateFlow()

    private val _categories = MutableStateFlow<Category?>(null)
    val categories = _categories.asStateFlow()

    private val _selectedCategories = MutableStateFlow<List<CategoryDetail>>(emptyList())
    val selectedCategories = _selectedCategories.asStateFlow()

    init {
        viewModelScope.launch {
            languageRepository.myLanguageKey.collectLatest {
                refresh()
            }
        }
    }

    fun refresh() {
        viewModelScope.apply {
            launch { fetchCategories() }
            launch { fetchAttractions() }
        }
    }

    private suspend fun fetchCategories() {
        kotlin.runCatching {
            loading()
            travelApiRepository.getCategories(CategoryType.Attractions)
        }.onSuccess {
            _categories.value = it
            noLoading()
            noRefreshing()
        }.onFailure {
            Log.e("AttractionViewModel-fetchCategories", it.message.toString())
            noLoading()
            noRefreshing()
        }
    }

    private suspend fun fetchAttractions() {
        kotlin.runCatching {
            loading()
            travelApiRepository.getAttractions(selectedCategories.value)
        }.onSuccess {
            _attractions.value = it
            noLoading()
            noRefreshing()
        }.onFailure {
            Log.e("AttractionViewModel-fetchCategories", it.message.toString())
            noLoading()
            noRefreshing()
        }
    }
}