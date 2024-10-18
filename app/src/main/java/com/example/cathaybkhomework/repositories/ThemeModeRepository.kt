package com.example.cathaybkhomework.repositories

import com.example.cathaybkhomework.ui.theme.ThemeMode
import com.example.myandroid.common.language.MyModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ThemeModeRepository {
    private val _themeMode: MutableStateFlow<ThemeMode> = MutableStateFlow(ThemeMode[MyModel.languageKey] ?: ThemeMode.SYSTEM)
    val themeMode = _themeMode.asStateFlow()

    fun setThemeMode(themeMode: ThemeMode) {
        _themeMode.value = themeMode
    }
}