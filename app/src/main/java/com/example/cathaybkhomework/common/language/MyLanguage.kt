package com.example.cathaybkhomework.common.language

import com.example.cathaybkhomework.common.language.multi.English
import com.example.cathaybkhomework.common.language.multi.Indonesian
import com.example.cathaybkhomework.common.language.multi.Japanese
import com.example.cathaybkhomework.common.language.multi.Korean
import com.example.cathaybkhomework.common.language.multi.SimpleChinese
import com.example.cathaybkhomework.common.language.multi.Spanish
import com.example.cathaybkhomework.common.language.multi.Thai
import com.example.cathaybkhomework.common.language.multi.TraditionalChinese
import com.example.cathaybkhomework.common.language.multi.Vietnamese
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
            get() = when (current) {
                TW -> TraditionalChinese
                CN -> SimpleChinese
                EN -> English
                JP -> Japanese
                KO -> Korean
                ES -> Spanish
                ID -> Indonesian
                TH -> Thai
                VI -> Vietnamese
            }

    }

    fun getString(): String = lang
}

