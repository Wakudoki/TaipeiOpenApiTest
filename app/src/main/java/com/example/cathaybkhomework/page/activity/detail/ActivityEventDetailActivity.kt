package com.example.cathaybkhomework.page.activity.detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.cathaybkhomework.common.composable.LocalColorBackgroundOriginal
import com.example.cathaybkhomework.common.composable.LocalColorTextTitle
import com.example.cathaybkhomework.common.composable.LocalLanguageOf
import com.example.cathaybkhomework.common.composable.LocalThemeModeOf
import com.example.cathaybkhomework.common.language.MyLanguage
import com.example.cathaybkhomework.data.ActivityEventItem
import com.example.cathaybkhomework.ui.theme.ThemeMode
import com.example.myandroid.common.language.MyModel
import com.google.gson.Gson

class ActivityEventDetailActivity : ComponentActivity() {

    companion object {
        fun newIntent(
            context: Context,
            title: String,
            activityEventItem: ActivityEventItem
        ): Intent {
            return Intent(context, ActivityEventDetailActivity::class.java).apply {
                putExtra("title", title)
                putExtra("activityEventItem", Gson().toJson(activityEventItem))
            }
        }
    }

    private val title by lazy {
        intent.getStringExtra("title") ?: MyLanguage.strings.attraction
    }

    private val activityEventItem by lazy {
        Gson().fromJson(intent.getStringExtra("activityEventItem"), ActivityEventItem::class.java)
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
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
                                        contentDescription = "Back"
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
                    ActivityEventDetailScreen(
                        modifier = Modifier
                            .background(LocalColorBackgroundOriginal)
                            .verticalScroll(rememberScrollState())
                            .padding(paddingValue),
                        activityEventItem
                    )
                }
            }
        }
    }


}