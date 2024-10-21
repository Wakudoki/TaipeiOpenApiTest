package com.example.cathaybkhomework.page.attraction.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cathaybkhomework.common.composable.EmptyScreen
import com.example.cathaybkhomework.common.composable.LocalColorBackgroundOriginal
import com.example.cathaybkhomework.common.composable.LocalColorBluePrimary
import com.example.cathaybkhomework.common.composable.LocalColorTextTitle
import com.example.cathaybkhomework.common.composable.TextGrid
import com.example.myandroid.extension.shimmerBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttractionScreen(
    viewModel: AttractionViewModel
) {
    val isRefreshing = viewModel.refreshingState.collectAsState()
    val isLoading = viewModel.loadingState.collectAsState()

    val categoriesData = viewModel.categories.collectAsState()
    val categories = categoriesData.value?.data?.category ?: emptyList()

    //讓空資料的提醒能置中
    val density = LocalDensity.current
    var sizeInDp by remember { mutableStateOf(DpSize.Zero) }

    PullToRefreshBox(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalColorBackgroundOriginal)
            .onSizeChanged {
                sizeInDp = density.run {
                    DpSize(
                        it.width.toDp(),
                        it.height.toDp()
                    )
                }
            },
        isRefreshing = isRefreshing.value,
        state = rememberPullToRefreshState(),
        onRefresh = {
            viewModel.refreshing()
            viewModel.refresh()
        }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            if (isLoading.value) {
                items(
                    count = 12
                ) {
                    Box(
                        modifier = Modifier
                            .width(160.dp)
                            .height(120.dp)
                            .shimmerBackground(RoundedCornerShape(4.dp))
                    )
                }
            } else if (categories.isNotEmpty()) {
                items(
                    count = categories.size
                ) { index ->
                    TextGrid(
                        modifier = Modifier
                            .width(160.dp)
                            .height(120.dp),
                        color = LocalColorTextTitle,
                        text = categories[index].name,
                        fontSize = 20.sp
                    )
                }
            } else {
                item {
                    EmptyScreen(sizeInDp)
                }
            }
        }
    }
}