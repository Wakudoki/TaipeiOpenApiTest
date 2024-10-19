package com.example.cathaybkhomework.common.language

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

        val strings: MultiLanguage
            get() = when(current) {
                TW -> TraditionalChinese
                else -> English
            }

    }

    fun getString(): String {
        val tw = mapOf(
            "latestNews" to "最新消息",
            "showMore" to "顯示更多",
            "home" to "首頁",
            "attraction" to "遊憩景點",
        )
        return lang
    }
}

