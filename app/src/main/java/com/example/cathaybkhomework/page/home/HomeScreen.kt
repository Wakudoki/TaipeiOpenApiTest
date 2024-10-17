package com.example.cathaybkhomework.page.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.example.cathaybkhomework.R
import com.example.cathaybkhomework.data.MyLanguage
import com.example.myandroid.extension.clickableNoRipple
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
) {
    val data = viewModel.events.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
//            .background(LocalColorBackgroundOriginal)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                TopBar(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp),
                    viewModel = viewModel
                )
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(
                    items = data.value?.data ?: emptyList(),
                    key = { _, item -> item.id },
                    contentType = { _, _ -> "EventItem" }
                ) { index, item ->
                    Text(
                        text = item.title
                    )
                }
            }
        }
    }
}

@Composable
private fun TopBar(
    modifier: Modifier,
    viewModel: HomeViewModel,
) {
    var languageExpandState by remember { mutableStateOf(false) }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(36.dp)
                .clickableNoRipple {

                },
            painter = painterResource(
//                id = if (isDarkMode) {
                R.drawable.ic_dark_mode_toggle
//                } else {
//                    R.drawable.ic_light_mode_toggle
//                }
            ),
            contentDescription = "ThemeMode",
//            tint = LocalColorIconNormal
        )

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            modifier = Modifier.clickableNoRipple {
                languageExpandState = !languageExpandState
            },
            painter = painterResource(id = R.drawable.ic_menu_more),
            contentDescription = "Menu",
//            tint = LocalColorIconNormal
        )

        LanguageMenu(
            expandState = languageExpandState,
            onSelect = { language ->
                languageExpandState = false
                viewModel.setLanguage(language.key)
            }
        ) { languageExpandState = false }
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
//        modifier = Modifier.background(LocalColorBackgroundOriginal),
        offset = DpOffset(16.dp, 2.dp),
        properties = PopupProperties(focusable = true)
    ) {
        MyLanguage.entries.forEach {
            DropdownMenuItem(
                text = {
                    Text(
                        text = it.getString(),
//                        color = LocalColorTextTitle
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