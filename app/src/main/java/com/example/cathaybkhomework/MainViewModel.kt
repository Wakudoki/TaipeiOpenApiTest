package com.example.cathaybkhomework

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cathaybkhomework.repositories.LanguageRepository
import com.example.cathaybkhomework.repositories.ThemeModeRepository
import com.example.cathaybkhomework.ui.theme.ThemeMode
import com.example.myandroid.common.language.MyModel
import kotlinx.coroutines.launch

class MainViewModel(
    private val languageRepository: LanguageRepository,
    private val themeModeRepository: ThemeModeRepository
) : ViewModel() {
    val languageKey = languageRepository.myLanguageKey
    val themeMode = themeModeRepository.themeMode

    fun setLanguage(languageKey: String) {
        viewModelScope.launch {
            MyModel.languageKey = languageKey
            languageRepository.setLanguage(languageKey)
        }
    }

    fun setThemeMode(themeMode: ThemeMode) {
        viewModelScope.launch {
            MyModel.themeMode = themeMode.key
            themeModeRepository.setThemeMode(themeMode)
        }
    }
}