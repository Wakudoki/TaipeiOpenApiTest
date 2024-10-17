package com.example.cathaybkhomework.common.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.example.cathaybkhomework.ui.theme.ThemeMode
import com.example.myandroid.common.language.MyModel
import com.example.myandroid.ui.theme.ColorScheme
import com.example.myandroid.ui.theme.DarkColor
import com.example.myandroid.ui.theme.LightColor

val LocalThemeModeOf @Composable get() = staticCompositionLocalOf { ThemeMode[MyModel.themeMode] }
val LocalThemeMode @Composable get() = LocalThemeModeOf.current
val LocalIsDarkTheme
    @Composable get() = when (LocalThemeMode) {
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.DARK -> true
        ThemeMode.LIGHT -> false
        null -> isSystemInDarkTheme()
    }

val LocalColorTextTitle @Composable get() = localColor("text.title") { text.title }
val LocalColorTextSubtitle @Composable get() = localColor("text.subtitle") { text.subtitle }

val LocalColorBackgroundOriginal @Composable get() = localColor("background.original") { background.original }

val LocalColorLine1 @Composable get() = localColor("line.line1") { line.line1 }
val LocalColorLine2 @Composable get() = localColor("line.line2") { line.line2 }
val LocalColorLine3 @Composable get() = localColor("line.line3") { line.line3 }
val LocalColorLine4 @Composable get() = localColor("line.line4") { line.line4 }

val LocalColorIconNormal @Composable get() = localColor("icon.normal") { icon.normal }

val LocalColorBluePrimary @Composable get() = localColor("blue.primary") { blue.primary }
val LocalColorBlueSecondary @Composable get() = localColor("blue.secondary") { blue.secondary }

val LocalColorPurplePrimary @Composable get() = localColor("purple.primary") { purple.primary }
val LocalColorPurpleSecondary @Composable get() = localColor("purple.secondary") { purple.secondary }

val LocalColorStatusWarning @Composable get() = localColor("status.warning") { status.warning }
val LocalColorStatusSuccess @Composable get() = localColor("status.success") { status.success }

@Composable
private fun localColor(label: String, invoke: ColorScheme.() -> Color) =
    animateColorAsState(
        targetValue = if (LocalIsDarkTheme) {
            DarkColor
        } else {
            LightColor
        }.invoke(),
        label = label,
    ).value