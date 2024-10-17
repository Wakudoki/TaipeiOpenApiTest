package com.example.cathaybkhomework.ui.theme

/**
 * @property key data store storage, DO NOT modify
 */
enum class ThemeMode(val key: String) {
    SYSTEM("system"),
    DARK("dark"),
    LIGHT("light"),
    ;

    companion object {
        val default = SYSTEM
        operator fun get(key: String?) = entries.find { it.key == key }
    }
}