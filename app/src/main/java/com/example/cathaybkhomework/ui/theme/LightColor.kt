package com.example.myandroid.ui.theme

import androidx.compose.ui.graphics.Color

object LightColor: ColorScheme {
    override val text: ColorScheme.Text
        get() = ColorScheme.Text(
            title = Color(0xff333333),
            subtitle = Color(0xff454545),
        )
    override val background: ColorScheme.Background
        get() = ColorScheme.Background(
            original = Color(0xffF4F4F4)
        )
    override val line: ColorScheme.Line
        get() = ColorScheme.Line(
            line1 = Color(0xffF4F4F4),
            line2 = Color(0xffEEEEEE),
            line3 = Color(0xffD8D8D8),
            line4 = Color(0xff999999)
        )
    override val status: ColorScheme.Status
        get() = ColorScheme.Status(
            warning = Color(0xffC01818),
            success = Color(0xff0AA03D),
        )
    override val icon: ColorScheme.Icon
        get() = ColorScheme.Icon(
            normal = Color(0xff666666)
        )
    override val blue: ColorScheme.Blue
        get() = ColorScheme.Blue(
            primary = Color(0xFF0099EC),
            secondary = Color(0xFF2B71C7),
            tertiary = Color(0xFF1B5FAE)
        )
    override val purple: ColorScheme.Purple
        get() = ColorScheme.Purple(
            primary = Color(0xFF6650a4),
            secondary = Color(0xFF625b71),
            tertiary = Color(0xFF7D5260)
        )
}