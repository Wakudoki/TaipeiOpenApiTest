package com.example.cathaybkhomework.page.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cathaybkhomework.common.composable.LocalColorBackgroundOriginal
import com.example.cathaybkhomework.common.composable.LocalColorTextTitle
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
) {
    val data = viewModel.events.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalColorBackgroundOriginal)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(
                items = data.value?.data ?: emptyList(),
                key = { _, item -> item.id },
                contentType = { _, _ -> "EventItem" }
            ) { index, item ->
                Text(
                    text = item.title,
                    color = LocalColorTextTitle
                )
            }
        }
    }
}