package com.example.cathaybkhomework.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

object TimeUtils {
    private const val InputFormat = "yyyy-MM-dd HH:mm:ss"
    val yyyyMMddHHmm = "yyyy-MM-dd HH:mm"
    val yyyyMMdd = "yyyy-MM-dd"
    val yyyyMM = "yyyy-MM"

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

    fun getDurationDate(
        beginTimeString: String,
        endTimeString: String
    ): String {
        val beginDate = parseTime(
            timeString = beginTimeString,
            outputFormat = yyyyMMdd
        )
        val endDate = parseTime(
            timeString = endTimeString,
            outputFormat = yyyyMMdd
        )

        return if (beginDate == endDate) {
            beginDate
        } else {
            "$beginDate ~ $endDate"
        }
    }
}