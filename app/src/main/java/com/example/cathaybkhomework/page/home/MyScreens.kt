package com.example.cathaybkhomework.page.home

import androidx.compose.ui.graphics.vector.ImageVector

enum class MyScreens {
    Home,
    Attraction
}

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val route: String
)