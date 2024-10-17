package com.example.cathaybkhomework.repositories

import com.example.myandroid.common.language.MyModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LanguageRepository {
    private val _myLanguageKey: MutableStateFlow<String> = MutableStateFlow(MyModel.languageKey)
    val myLanguageKey = _myLanguageKey.asStateFlow()

    fun setLanguage(languageKey: String) {
        _myLanguageKey.value = languageKey
    }
}