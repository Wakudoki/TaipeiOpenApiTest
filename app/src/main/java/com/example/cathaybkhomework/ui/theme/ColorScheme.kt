package com.example.myandroid.ui.theme

import androidx.compose.ui.graphics.Color

interface ColorScheme {
    val text: Text
    val background: Background
    val line: Line
    val status: Status
    val icon: Icon
    val blue: Blue
    val purple: Purple

    data class Text(
        val title: Color,
        val subtitle: Color
    )

    data class Background(
        val original: Color,
        val secondary: Color
    )

    data class Line(
        val line1: Color,
        val line2: Color,
        val line3: Color,
        val line4: Color
    )

    data class Status(
        val success: Color,
        val warning: Color
    )

    data class Icon(
        val normal: Color
    )

    data class Blue(
        val primary: Color,
        val secondary: Color,
        val tertiary: Color
    )

    data class Purple(
        val primary: Color,
        val secondary: Color,
        val tertiary: Color
    )
}