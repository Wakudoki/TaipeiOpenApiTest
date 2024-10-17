package com.example.cathaybkhomework.data

import com.example.myandroid.common.language.MyModel

enum class MyLanguage(
    val lang: String,
    val key: String
) {
    TW("正體中文", "zh-tw"),
    CN("简体中文", "zh-cn"),
    EN("English", "en"),
    JP("日本語", "ja"),
    KO("한국어", "ko"),
    ES("español", "es"),
    ID("Indonesia", "id"),
    TH("แบบไทย", "th"),
    VI("Tiếng Việt", "vi"),
    ;

    companion object {
        val default by lazy { EN }
        val current get() = MyLanguage[MyModel.languageKey] ?: default
        operator fun get(key: String) = entries.find { it.key == key }
    }

    fun getString(): String {
        return lang
    }
}