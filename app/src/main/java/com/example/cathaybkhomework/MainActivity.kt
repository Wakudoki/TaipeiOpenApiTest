package com.example.cathaybkhomework

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cathaybkhomework.common.composable.LocalColorBackgroundOriginal
import com.example.cathaybkhomework.common.composable.LocalColorIconNormal
import com.example.cathaybkhomework.common.composable.LocalColorLine2
import com.example.cathaybkhomework.common.composable.LocalColorTextTitle
import com.example.cathaybkhomework.common.composable.LocalIsDarkTheme
import com.example.cathaybkhomework.common.composable.LocalThemeModeOf
import com.example.cathaybkhomework.data.MyLanguage
import com.example.cathaybkhomework.page.home.BottomNavigationItem
import com.example.cathaybkhomework.page.home.HomeScreen
import com.example.cathaybkhomework.page.home.MyScreens
import com.example.cathaybkhomework.ui.theme.ThemeMode
import com.example.myandroid.extension.clickableNoRipple

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val themeMode = viewModel.themeMode.collectAsState()
            val navController = rememberNavController()
            var languageExpandState by remember { mutableStateOf(false) }
            val isDarkMode = LocalIsDarkTheme

            val systemBarStyle = if (isDarkMode) {
                SystemBarStyle.dark(Color.TRANSPARENT)
            } else {
                SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
            }
            enableEdgeToEdge(
                statusBarStyle = systemBarStyle,
                navigationBarStyle = systemBarStyle
            )

            CompositionLocalProvider(
                LocalThemeModeOf provides themeMode.value
            ) {
                Scaffold(
                    contentColor = LocalColorBackgroundOriginal,
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                titleContentColor = LocalColorTextTitle,
                                containerColor = LocalColorBackgroundOriginal
                            ),
                            title = { Text(text = "TopAppBar") },
                            actions = {
                                Icon(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clickableNoRipple {
                                            viewModel.setThemeMode(
                                                if (isDarkMode) {
                                                    ThemeMode.LIGHT
                                                } else {
                                                    ThemeMode.DARK
                                                }
                                            )
                                        },
                                    painter = painterResource(
                                        id = if (isDarkMode) {
                                            R.drawable.ic_dark_mode_toggle
                                        } else {
                                            R.drawable.ic_light_mode_toggle
                                        }
                                    ),
                                    contentDescription = "ThemeMode",
                                    tint = LocalColorIconNormal
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Icon(
                                    modifier = Modifier.clickableNoRipple {
                                        languageExpandState = !languageExpandState
                                    },
                                    imageVector = Icons.Filled.Menu,
                                    contentDescription = "DateRange",
                                    tint = LocalColorIconNormal
                                )

                                LanguageMenu(
                                    expandState = languageExpandState,
                                    onSelect = { language ->
                                        languageExpandState = false
                                        viewModel.setLanguage(language.key)
                                    }
                                ) { languageExpandState = false }
                            }
                        )
                    },
                    bottomBar = {
                        NavigationBar(
                            containerColor = LocalColorBackgroundOriginal,
                            contentColor = LocalColorLine2
                        ) {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentRoute = navBackStackEntry?.destination
                            var selectedItemIndex by rememberSaveable {
                                mutableIntStateOf(0)
                            }
                            val items = listOf(
                                BottomNavigationItem(
                                    title = "Home",
                                    selectedIcon = Icons.Filled.Home,
                                    unSelectedIcon = Icons.Outlined.Home,
                                    route = MyScreens.Home.name
                                ),
                                BottomNavigationItem(
                                    title = "Attraction",
                                    selectedIcon = Icons.Filled.LocationOn,
                                    unSelectedIcon = Icons.Filled.LocationOn,
                                    route = MyScreens.Attraction.name
                                )
                            )
                            items.forEachIndexed { index, item ->
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
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = MyScreens.Home.name,
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable(route = MyScreens.Home.name) {
                            HomeScreen()
                        }
                        composable(route = MyScreens.Attraction.name) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Text(
                                    modifier = Modifier.align(Alignment.Center),
                                    text = "Attraction"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LanguageMenu(
    expandState: Boolean,
    onSelect: (language: MyLanguage) -> Unit,
    onDismissRequest: () -> Unit
) {
    DropdownMenu(
        expanded = expandState,
        onDismissRequest = {
            onDismissRequest()
        },
        modifier = Modifier.background(LocalColorBackgroundOriginal),
        offset = DpOffset(16.dp, 2.dp),
        properties = PopupProperties(focusable = true)
    ) {
        MyLanguage.entries.forEach {
            DropdownMenuItem(
                text = {
                    Text(
                        text = it.getString(),
                        color = LocalColorTextTitle
                    )
                },
                onClick = {
                    onSelect(it)
                },
                enabled = true
            )
        }
    }
}