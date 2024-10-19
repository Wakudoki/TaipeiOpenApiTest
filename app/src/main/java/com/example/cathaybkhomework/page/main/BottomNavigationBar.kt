package com.example.cathaybkhomework.page.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cathaybkhomework.common.composable.LocalColorBackgroundOriginal
import com.example.cathaybkhomework.common.composable.LocalColorIconNormal
import com.example.cathaybkhomework.common.composable.LocalColorLine2
import com.example.cathaybkhomework.common.composable.LocalColorTextTitle
import com.example.cathaybkhomework.common.language.MyLanguage
import com.example.cathaybkhomework.data.MyScreens

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

@Composable
fun BottomNavigationBar(
    navController: NavController,
    title: MutableState<String>
) {
    NavigationBar(
        containerColor = LocalColorBackgroundOriginal,
        contentColor = LocalColorLine2
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination
        val selectedItemIndex by rememberSaveable {
            mutableIntStateOf(0)
        }

        NavigationItem.entries.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = currentRoute?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    when (item.route) {
                        MyScreens.Home.name -> {
                            title.value = MyLanguage.strings.home
                        }
                        MyScreens.Attraction.name -> {
                            title.value = MyLanguage.strings.attraction
                        }
                    }
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState
                        }
                        launchSingleTop = true
                        restoreState = true
                    }

                },
                icon = {
                    Icon(
                        imageVector = if (index == selectedItemIndex) {
                            item.selectedIcon
                        } else item.unSelectedIcon,
                        contentDescription = item.route,
                        tint = LocalColorIconNormal
                    )
                },
                label = {
                    Text(
                        text = when (item.route) {
                            MyScreens.Home.name -> MyLanguage.strings.home
                            MyScreens.Attraction.name -> MyLanguage.strings.attraction
                            else -> MyLanguage.strings.home
                        },
                        color = LocalColorTextTitle
                    )
                },
            )
        }
    }
}