package com.example.cathaybkhomework

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cathaybkhomework.common.composable.FragmentContainer
import com.example.cathaybkhomework.common.composable.LocalColorBackgroundOriginal
import com.example.cathaybkhomework.common.composable.LocalColorBackgroundSecondary
import com.example.cathaybkhomework.common.composable.LocalColorBlueSecondary
import com.example.cathaybkhomework.common.composable.LocalColorIconNormal
import com.example.cathaybkhomework.common.composable.LocalColorTextTitle
import com.example.cathaybkhomework.common.composable.LocalIsDarkTheme
import com.example.cathaybkhomework.common.composable.LocalLanguageOf
import com.example.cathaybkhomework.common.composable.LocalThemeModeOf
import com.example.cathaybkhomework.common.language.MyLanguage
import com.example.cathaybkhomework.data.MyScreens
import com.example.cathaybkhomework.data.NavigationItem
import com.example.cathaybkhomework.page.attraction.AttractionFragment
import com.example.cathaybkhomework.page.home.HomeFragment
import com.example.cathaybkhomework.ui.theme.ThemeMode
import com.example.myandroid.common.language.MyModel
import com.example.myandroid.extension.clickableNoRipple
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : FragmentActivity() {
    private val viewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach { fragment ->
            if (fragment != null && fragment.isVisible) {
                with(fragment.childFragmentManager) {
                    if (backStackEntryCount > 0) {
                        popBackStack()
                        return
                    }
                }
            }
        }
        super.onBackPressed()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    @SuppressLint(
        "UnusedMaterialScaffoldPaddingParameter",
        "UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState"
    )
    fun MainScreen() {
        //判斷是否為暗黑模式，並修改status&navigationBar的顏色
        val themeMode = viewModel.themeMode.collectAsState()
        val language = viewModel.languageKey.collectAsState()
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
        val navController = rememberNavController()
        var languageExpandState by remember { mutableStateOf(false) }
        val title = mutableStateOf(
            when (navController.currentDestination?.route) {
                MyScreens.Home.name -> MyLanguage.strings.home
                MyScreens.Attraction.name -> MyLanguage.strings.attraction
                else -> MyLanguage.strings.home
            }
        )

        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        CompositionLocalProvider(
            LocalThemeModeOf provides themeMode.value,
            LocalLanguageOf provides MyLanguage[language.value]
        ) {
            NavigationDrawer(
                scope = scope,
                drawerState = drawerState,
                navController = navController,
                title = title
            ) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            navigationIcon = {
                                IconButton(
                                    onClick = {
                                        drawerState.apply {
                                            scope.launch {
                                                if (isClosed)
                                                    open()
                                                else
                                                    close()
                                            }
                                        }
                                    }
                                ) {
                                    Icon(  //Show Menu Icon on TopBar
                                        imageVector = Icons.Default.Menu,
                                        contentDescription = "Menu",
                                        tint = LocalColorIconNormal
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                titleContentColor = LocalColorTextTitle,
                                containerColor = LocalColorBackgroundOriginal
                            ),
                            title = { Text(text = title.value) },
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
                                    painter = painterResource(R.drawable.ic_language),
                                    contentDescription = "DateRange",
                                    tint = LocalColorIconNormal
                                )

                                Spacer(modifier = Modifier.padding(end = 2.dp))

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
                ) { innerPadding ->
                    Navigation(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        navController = navController,
                        supportFragmentManager = supportFragmentManager,
                        getCommitFunction = ::getCommitFunction
                    )
                }
            }
        }
    }

    @Composable
    fun Navigation(
        modifier: Modifier = Modifier,
        navController: NavHostController,
        supportFragmentManager: FragmentManager,
        getCommitFunction: (
            fragment: Fragment,
            tag: String
        ) -> (FragmentTransaction.(containerId: Int) -> Unit)
    ) {
        NavHost(navController, startDestination = NavigationItem.Home.route) {
            enumValues<NavigationItem>().forEach { item ->
                composable(item.route) {
                    val fragment = when (item.route) {
                        MyScreens.Home.name -> HomeFragment()
                        MyScreens.Attraction.name -> AttractionFragment()
                        else -> HomeFragment()
                    }

                    FragmentContainer(
                        modifier = modifier,
                        fragmentManager = supportFragmentManager,
                        commit = getCommitFunction(
                            fragment,
                            item.route
                        )
                    )
                }
            }
        }
    }

    private fun getCommitFunction(
        fragment: Fragment,
        tag: String
    ): FragmentTransaction.(containerId: Int) -> Unit = {
        replace(it, fragment, tag)
    }

    @Composable
    private fun NavigationDrawer(
        scope: CoroutineScope,
        drawerState: DrawerState,
        navController: NavHostController,
        title: MutableState<String>,
        content: @Composable () -> Unit
    ) {
        var selectedItemIndex by rememberSaveable {
            mutableIntStateOf(0)
        }

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Spacer(modifier = Modifier.height(16.dp)) //space (margin) from top
                    NavigationItem.entries.forEachIndexed { index, item ->
                        NavigationDrawerItem(
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
                            selected = index == selectedItemIndex,
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

                                selectedItemIndex = index
                                scope.launch {
                                    drawerState.close()
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = if (index == selectedItemIndex) {
                                        item.selectedIcon
                                    } else item.unSelectedIcon,
                                    contentDescription = item.route
                                )
                            },
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding) //padding between items
                        )
                    }

                }
            },
            gesturesEnabled = true
        ) {
            content.invoke()
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
                        text = MyLanguage.strings.selectLanguage,
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
}