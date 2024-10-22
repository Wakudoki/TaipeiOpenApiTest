package com.example.cathaybkhomework.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

object TimeUtils {
    private const val InputFormat = "yyyy-MM-dd HH:mm:ss"
    val yyyyMMddHHmm = "yyyy-MM-dd HH:mm"
    val yyyyMMdd = "yyyy-MM-dd"

    @SuppressLint("SimpleDateFormat")
    fun parseTime(
        timeString: String,
        inputFormat: String = this.InputFormat,
        outputFormat: String = this.yyyyMMddHHmm
    ): String {
        val parser = SimpleDateFormat(inputFormat)
        val formatter = SimpleDateFormat(outputFormat)
        return parser.parse(timeString)?.let { formatter.format(it) } ?: timeString
    }
}