package com.example.cathaybkhomework.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

object TimeUtils {
    @SuppressLint("SimpleDateFormat")
    fun parseTime(timeString: String): String {
        val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
        return parser.parse(timeString)?.let { formatter.format(it) } ?: timeString
    }
}