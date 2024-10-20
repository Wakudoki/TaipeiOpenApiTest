package com.example.cathaybkhomework.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

enum class NavigationItem(
    val route: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
) {
    Home(
        route = MyScreens.Home.name,
        selectedIcon = Icons.Filled.Home,
        unSelectedIcon = Icons.Outlined.Home,
    ),
    Attraction(
        route = MyScreens.Attraction.name,
        selectedIcon = Icons.Filled.LocationOn,
        unSelectedIcon = Icons.Filled.LocationOn,
    ),
    ;
}