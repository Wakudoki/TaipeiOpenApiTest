package com.example.cathaybkhomework.page.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.cathaybkhomework.MainViewModel
import com.example.cathaybkhomework.R
import com.example.cathaybkhomework.common.composable.LocalColorBackgroundOriginal
import com.example.cathaybkhomework.common.composable.LocalColorBackgroundSecondary
import com.example.cathaybkhomework.common.composable.LocalColorBlueSecondary
import com.example.cathaybkhomework.common.composable.LocalColorIconNormal
import com.example.cathaybkhomework.common.composable.LocalColorTextTitle
import com.example.cathaybkhomework.common.composable.LocalIsDarkTheme
import com.example.cathaybkhomework.common.language.MyLanguage
import com.example.cathaybkhomework.ui.theme.ThemeMode
import com.example.myandroid.common.language.MyModel
import com.example.myandroid.extension.clickableNoRipple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    viewModel: MainViewModel,
    title: MutableState<String>
) {
    var languageExpandState by remember { mutableStateOf(false) }
    val isDarkMode = LocalIsDarkTheme
    TopAppBar(
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