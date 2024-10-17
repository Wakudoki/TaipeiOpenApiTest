package com.example.cathaybkhomework

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Home
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cathaybkhomework.common.composable.LocalColorBackgroundOriginal
import com.example.cathaybkhomework.common.composable.LocalColorBackgroundSecondary
import com.example.cathaybkhomework.common.composable.LocalColorBlueSecondary
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
import com.example.myandroid.common.language.MyModel
import com.example.myandroid.extension.clickableNoRipple
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by inject()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val themeMode = viewModel.themeMode.collectAsState()

            val navController = rememberNavController()
            var languageExpandState by remember { mutableStateOf(false) }

            //判斷是否為暗黑模式，並修改status&navigationBar的顏色
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


                                if (languageExpandState) {
                                    LanguageDialog(
                                        onSelect = { language ->
                                            languageExpandState = false
                                            if (language.key != MyModel.languageKey) {   //與目前不同才做更新
                                                viewModel.setLanguage(language.key)
                                            }
                                        },
                                    ) { languageExpandState = false }
                                }
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
                            val selectedItemIndex by rememberSaveable {
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
private fun LanguageDialog(
    onSelect: (language: MyLanguage) -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        LazyColumn(
            modifier = Modifier
                .background(LocalColorBackgroundSecondary)
                .padding(16.dp)
                .clip(shape = RoundedCornerShape(8.dp)),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "選擇語言",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    ),
                    color = LocalColorTextTitle
                )
            }

            items(
                items = MyLanguage.entries
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = if (it == MyLanguage.current) {
                                LocalColorBlueSecondary.copy(alpha = 0.4f)
                            } else {
                                androidx.compose.ui.graphics.Color.Transparent
                            }
                        )
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .clickableNoRipple {
                            onSelect(it)
                        }
                        .clip(shape = RoundedCornerShape(8.dp)),
                    text = it.getString(),
                    color = if (it == MyLanguage.current) {
                        androidx.compose.ui.graphics.Color.White
                    } else {
                        LocalColorTextTitle
                    },
                )
            }
        }
    }
}