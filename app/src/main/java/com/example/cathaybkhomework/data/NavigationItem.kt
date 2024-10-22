package com.example.cathaybkhomework.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.ThumbUp
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
        unSelectedIcon = Icons.Outlined.LocationOn,
    ),
    ActivityEvent(
        route = MyScreens.ActivityEvent.name,
        selectedIcon = Icons.Filled.Info,
        unSelectedIcon = Icons.Outlined.Info,
    ),
    EventCalendar(
        route = MyScreens.EventCalendar.name,
        selectedIcon = Icons.Filled.DateRange,
        unSelectedIcon = Icons.Outlined.DateRange,
    ),
    Tours(
        route = MyScreens.Tours.name,
        selectedIcon = Icons.Filled.ThumbUp,
        unSelectedIcon = Icons.Outlined.ThumbUp,
    ),
    ;
}