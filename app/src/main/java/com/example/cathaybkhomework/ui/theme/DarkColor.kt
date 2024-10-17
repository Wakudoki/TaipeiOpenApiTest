package com.example.myandroid.ui.theme

import androidx.compose.ui.graphics.Color

object DarkColor: ColorScheme {
    override val text: ColorScheme.Text
        get() = ColorScheme.Text(
            title = Color(0xffF0F3F4),
            subtitle = Color(0xffDDDDDD),
        )
    override val background: ColorScheme.Background
        get() = ColorScheme.Background(
            original = Color(0xff22292E),
            secondary = Color(0xff3C3F40)
        )
    override val line: ColorScheme.Line
        get() = ColorScheme.Line(
            line1 = Color(0xff22292E),
            line2 = Color(0xff3C3F40),
            line3 = Color(0xff40464A),
            line4 = Color(0xff545859)
        )
    override val status: ColorScheme.Status
        get() = ColorScheme.Status(
            warning = Color(0xffE14B51),
            success = Color(0xff77B867),
        )
    override val icon: ColorScheme.Icon
        get() = ColorScheme.Icon(
            normal = Color(0xffA5AEB0)
        )
    override val blue: ColorScheme.Blue
        get() = ColorScheme.Blue(
            primary = Color(0xFF0099EC),
            secondary = Color(0xFF27A6FF),
            tertiary = Color(0xFF1B5FAE)
        )
    override val purple: ColorScheme.Purple
        get() = ColorScheme.Purple(
            primary = Color(0xFFD0BCFF),
            secondary = Color(0xFFCCC2DC),
            tertiary = Color(0xFFEFB8C8)
        )
}