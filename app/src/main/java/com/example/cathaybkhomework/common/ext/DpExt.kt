package com.example.myandroid.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

val Int.textDp: TextUnit
    @Composable get() = with(LocalDensity.current) {
        this@textDp.dp.toSp()
    }

val Float.textDp: TextUnit
    @Composable get() = with(LocalDensity.current) {
        this@textDp.dp.toSp()
    }