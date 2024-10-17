package com.example.cathaybkhomework

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cathaybkhomework.repositories.LanguageRepository
import com.example.cathaybkhomework.ui.theme.ThemeMode
import com.example.myandroid.common.language.MyModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val languageRepository: LanguageRepository
) : ViewModel() {
    private val _themeMode = MutableStateFlow(ThemeMode[MyModel.themeMode])
    val themeMode = _themeMode.asStateFlow()

    fun setLanguage(languageKey: String) {
        viewModelScope.launch {
            MyModel.languageKey = languageKey
            languageRepository.setLanguage(languageKey)
        }
    }

    fun setThemeMode(themeMode: ThemeMode) {
        viewModelScope.launch {
            MyModel.themeMode = themeMode.key
            _themeMode.value = themeMode
        }
    }
}