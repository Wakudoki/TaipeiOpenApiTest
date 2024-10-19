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
import com.example.cathaybkhomework.data.MyScreens

enum class NavigationItem(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
) {
    Home(
        route = MyScreens.Home.name,
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unSelectedIcon = Icons.Outlined.Home,
    ),
    Attraction(
        route = MyScreens.Attraction.name,
        title = "Attraction",
        selectedIcon = Icons.Filled.LocationOn,
        unSelectedIcon = Icons.Filled.LocationOn,
    ),
    ;
}

@Composable
fun BottomNavigationBar(navController: NavController) {
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
                        contentDescription = item.title,
                        tint = LocalColorIconNormal
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        color = LocalColorTextTitle
                    )
                },

                )
        }
    }
}