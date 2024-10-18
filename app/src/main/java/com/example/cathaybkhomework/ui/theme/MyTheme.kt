package com.example.cathaybkhomework.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import com.example.cathaybkhomework.common.composable.LocalThemeModeOf
import com.example.cathaybkhomework.repositories.ThemeModeRepository
import org.koin.compose.koinInject

@Composable
fun MyTheme(content: @Composable () -> Unit) {
    val themeModeRepository: ThemeModeRepository = koinInject()
    //判斷是否為暗黑模式，並修改status&navigationBar的顏色
    val themeMode = themeModeRepository.themeMode.collectAsState()
    CompositionLocalProvider(
        LocalThemeModeOf provides themeMode.value
    ) {
        content.invoke()
    }
}