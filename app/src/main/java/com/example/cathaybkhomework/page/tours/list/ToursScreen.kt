package com.example.cathaybkhomework.page.tours.list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cathaybkhomework.R
import com.example.cathaybkhomework.common.composable.EmptyScreen
import com.example.cathaybkhomework.common.composable.LoadingGradient
import com.example.cathaybkhomework.common.composable.LocalColorBackgroundOriginal
import com.example.cathaybkhomework.common.composable.LocalColorBackgroundSecondary
import com.example.cathaybkhomework.common.composable.LocalColorBluePrimary
import com.example.cathaybkhomework.common.composable.LocalColorLine3
import com.example.cathaybkhomework.common.composable.LocalColorTextTitle
import com.example.cathaybkhomework.common.language.MyLanguage
import com.example.cathaybkhomework.data.TourItem
import com.example.cathaybkhomework.page.tours.detail.ToursDetailActivity
import com.example.myandroid.extension.clickableNoRipple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToursScreen(
    viewModel: ToursViewModel
) {
    val isRefreshing = viewModel.refreshingState.collectAsState()
    val isLoading = viewModel.loadingState.collectAsState()

    val toursData = viewModel.tours.collectAsState()
    val tours = toursData.value?.data ?: emptyList()

    val context = LocalContext.current

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
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (isLoading.value) {
                LoadingGradient(
                    count = 10,
                    height = 80.dp
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (tours.isEmpty()) {
                        item {
                            EmptyScreen(sizeInDp)
                        }
                    } else {
                        items(
                            count = tours.size
                        ) { index ->
                            ToursCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                tourItem = tours[index]
                            ) {
                                context.startActivity(
                                    ToursDetailActivity.newIntent(
                                        context = context,
                                        title = it.title,
                                        tourItem = it
                                    )
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
fun ToursCard(
    modifier: Modifier = Modifier,
    tourItem: TourItem,
    onClick: (tourItem: TourItem) -> Unit
) {
    Row(
        modifier = modifier
            .background(LocalColorBackgroundSecondary)
            .border(width = 1.dp, color = LocalColorLine3)
            .padding(8.dp)
            .clickableNoRipple { onClick.invoke(tourItem) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
                .padding(bottom = 4.dp)
        ) {
            Text(
                text = tourItem.title,
                style = TextStyle(
                    fontSize = 16.sp
                ),
                color = LocalColorTextTitle
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = MyLanguage.strings.viewDetail,
                    style = TextStyle(
                        fontSize = 12.sp,
                    ),
                    color = LocalColorBluePrimary
                )

                Icon(
                    modifier = Modifier.rotate(90f),
                    painter = painterResource(R.drawable.ic_up_arrow),
                    contentDescription = "view detail",
                    tint = LocalColorBluePrimary
                )
            }
        }
    }
}