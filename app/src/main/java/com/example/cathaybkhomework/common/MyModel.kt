package com.example.myandroid.common.language

import com.example.cathaybkhomework.common.datastore.MyDataStore
import com.example.cathaybkhomework.common.datastore.property

object MyModel {
    private val storage = MyDataStore()

    var themeMode by storage.property("ThemeMode", "")
    var languageKey by storage.property("LanguageKey", "")
}