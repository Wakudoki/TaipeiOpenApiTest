package com.example.cathaybkhomework.page.wabview

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import com.example.cathaybkhomework.common.composable.LocalColorBackgroundOriginal
import com.example.cathaybkhomework.common.composable.LocalColorIconNormal
import com.example.cathaybkhomework.common.composable.LocalColorTextTitle
import com.example.cathaybkhomework.common.composable.LocalIsDarkTheme
import com.example.cathaybkhomework.common.composable.LocalLanguageOf
import com.example.cathaybkhomework.common.composable.LocalThemeModeOf
import com.example.cathaybkhomework.common.language.MyLanguage
import com.example.cathaybkhomework.ui.theme.ThemeMode
import com.example.myandroid.common.language.MyModel


class WebViewActivity : ComponentActivity() {

    companion object {
        fun newIntent(
            context: Context,
            title: String,
            url: String
        ): Intent {
            return Intent(context, WebViewActivity::class.java).apply {
                putExtra("title", title)
                putExtra("url", url)
            }
        }
    }

    private val url by lazy {
        intent.getStringExtra("url") ?: "https://www.travel.taipei/"
    }

    private val title by lazy {
        intent.getStringExtra("title") ?: ""
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SetJavaScriptEnabled")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //判斷是否為暗黑模式，並修改status&navigationBar的顏色
            val isDarkMode = LocalIsDarkTheme
            val systemBarStyle = if (isDarkMode) {
                SystemBarStyle.dark(LocalColorBackgroundOriginal.toArgb())
            } else {
                SystemBarStyle.light(LocalColorBackgroundOriginal.toArgb(), LocalColorBackgroundOriginal.toArgb())
            }
            enableEdgeToEdge(
                statusBarStyle = systemBarStyle,
                navigationBarStyle = systemBarStyle
            )

            CompositionLocalProvider(
                LocalThemeModeOf provides ThemeMode[MyModel.themeMode],
                LocalLanguageOf provides MyLanguage[MyModel.languageKey]
            ) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            navigationIcon = {
                                IconButton(
                                    onClick = {
                                        onBackPressed()
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = "Back",
                                        tint = LocalColorIconNormal
                                    )
                                }
                            },
                            title = {
                                Text(text = title)
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                titleContentColor = LocalColorTextTitle,
                                containerColor = LocalColorBackgroundOriginal
                            )
                        )
                    },
                ) { paddingValue ->
                    AndroidView(
                        modifier = Modifier
                            .background(LocalColorBackgroundOriginal)
                            .padding(paddingValue),
                        factory = {
                            WebView(it).apply {
                                this.layoutParams = ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT
                                )
                                if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                                    WebSettingsCompat.setForceDark(
                                        this.settings,
                                        WebSettingsCompat.FORCE_DARK_AUTO
                                    )
                                }
                                settings.apply {
                                    //支援js交互
                                    javaScriptEnabled = true
                                    //將圖片調整到適合webView的大小
                                    useWideViewPort = true
                                    //縮放至螢幕的大小
                                    loadWithOverviewMode = true
                                    //縮放操作
                                    setSupportZoom(false)
                                    builtInZoomControls = true
                                    displayZoomControls = true
                                    //是否支援透過JS開啟新視窗
                                    javaScriptCanOpenWindowsAutomatically = true
                                    //不載入快取內容
                                    cacheMode = WebSettings.LOAD_NO_CACHE
                                    //部分網頁需開啟dom storage才能加載
                                    domStorageEnabled = true
                                }

                                setWebChromeClient(object : WebChromeClient() {
                                    override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
                                        Log.d(
                                            "MyApplication",
                                            (consoleMessage.message() + " -- From line "
                                                    + consoleMessage.lineNumber() + " of "
                                                    + consoleMessage.sourceId())
                                        )
                                        return super.onConsoleMessage(consoleMessage)
                                    }
                                })
                            }
                        },
                        update = {
                            it.loadUrl(url)
                        }
                    )
                }
            }
        }
    }
}