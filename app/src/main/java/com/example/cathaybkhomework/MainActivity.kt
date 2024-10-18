package com.example.cathaybkhomework

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cathaybkhomework.common.composable.FragmentContainer
import com.example.cathaybkhomework.common.composable.LocalIsDarkTheme
import com.example.cathaybkhomework.common.composable.LocalThemeModeOf
import com.example.cathaybkhomework.page.attraction.AttractionFragment
import com.example.cathaybkhomework.page.home.HomeFragment
import com.example.cathaybkhomework.page.home.MyScreens
import com.example.cathaybkhomework.page.main.BottomNavigationBar
import com.example.cathaybkhomework.page.main.NavigationItem
import com.example.cathaybkhomework.page.main.TopBar
import com.example.cathaybkhomework.ui.theme.ThemeMode
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

    @Composable
    @SuppressLint(
        "UnusedMaterialScaffoldPaddingParameter",
        "UnusedMaterial3ScaffoldPaddingParameter"
    )
    fun MainScreen() {
        //判斷是否為暗黑模式，並修改status&navigationBar的顏色
        val themeMode = viewModel.themeMode.collectAsState()
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
        CompositionLocalProvider(
            LocalThemeModeOf provides themeMode.value
        ) {
            Scaffold(
                topBar = { TopBar(viewModel) },
                bottomBar = { BottomNavigationBar(navController) }
            ) { Navigation(navController, supportFragmentManager, ::getCommitFunction) }
        }
    }

    @Composable
    fun Navigation(
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
                        modifier = Modifier.fillMaxSize(),
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
}