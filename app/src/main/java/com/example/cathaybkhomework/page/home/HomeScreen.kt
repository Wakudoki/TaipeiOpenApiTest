package com.example.cathaybkhomework.page.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cathaybkhomework.R
import com.example.cathaybkhomework.common.composable.EmptyScreen
import com.example.cathaybkhomework.common.composable.LoadingGradient
import com.example.cathaybkhomework.common.composable.LocalColorBackgroundOriginal
import com.example.cathaybkhomework.common.composable.LocalColorBluePrimary
import com.example.cathaybkhomework.common.composable.LocalColorTextTitle
import com.example.cathaybkhomework.common.language.MyLanguage
import com.example.cathaybkhomework.page.attraction.detail.AttractionDetailActivity
import com.example.cathaybkhomework.page.wabview.WebViewActivity
import com.example.myandroid.extension.clickableNoRipple
import kotlin.math.min

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
) {
    val context = LocalContext.current
    val newsData = viewModel.news.collectAsState()
    val attractionData = viewModel.attractions.collectAsState()
    val news = newsData.value?.data ?: emptyList()
    val attractions = attractionData.value?.data ?: emptyList()

    val isNewsLoading = viewModel.loadingNewsState.collectAsState()
    val isAttractionLoading = viewModel.loadingAttractionState.collectAsState()
    val isRefreshing = viewModel.refreshingState.collectAsState()

    val newsDisplayCount = remember { mutableIntStateOf(3) }
    val attractionsDisplayCount = remember { mutableIntStateOf(3) }

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
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (isNewsLoading.value) {
                item {
                    LoadingGradient()
                }
            } else if (news.isNotEmpty()) {
                item {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = MyLanguage.strings.latestNews,
                        style = TextStyle(
                            fontSize = 24.sp,
                            color = LocalColorTextTitle
                        )
                    )
                }

                items(
                    count = min(news.size, newsDisplayCount.intValue)
                ) { index ->
                    NewsCard(
                        news = news[index],
                        onClick = {
                            context.startActivity(
                                WebViewActivity.newIntent(
                                    context = context,
                                    url = it.url,
                                    title = MyLanguage.strings.latestNews
                                )
                            )
                        }
                    )
                }

                item {
                    //顯示更多
                    if (newsDisplayCount.intValue < news.size) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickableNoRipple {
                                    newsDisplayCount.intValue =
                                        min(news.size, newsDisplayCount.intValue + 3)
                                }
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                text = MyLanguage.strings.showMore,
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    color = LocalColorBluePrimary,
                                    textAlign = TextAlign.End
                                )
                            )

                            Icon(
                                modifier = Modifier
                                    .rotate(180f)
                                    .padding(horizontal = 4.dp),
                                painter = painterResource(R.drawable.ic_up_arrow),
                                contentDescription = "show more arrow",
                                tint = LocalColorBluePrimary
                            )
                        }
                    }
                }
            }

            if (isAttractionLoading.value) {
                item {
                    LoadingGradient()
                }
            } else if (attractions.isNotEmpty()) {
                item {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = MyLanguage.strings.attraction,
                        style = TextStyle(
                            fontSize = 24.sp,
                            color = LocalColorTextTitle
                        )
                    )
                }

                items(
                    count = min(attractions.size, attractionsDisplayCount.intValue)
                ) { index ->
                    AttractionCard(
                        attraction = attractions[index],
                        onClick = {
                            context.startActivity(
                                AttractionDetailActivity.newIntent(
                                    context = context,
                                    attractionItem = it,
                                    title = attractions[index].name
                                )
                            )
                        }
                    )
                }

                item {
                    //顯示更多
                    if (attractionsDisplayCount.intValue < attractions.size) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickableNoRipple {
                                    attractionsDisplayCount.intValue =
                                        min(news.size, attractionsDisplayCount.intValue + 3)
                                }
                                .padding(horizontal = 16.dp)
                                .padding(bottom = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                text = MyLanguage.strings.showMore,
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    color = LocalColorBluePrimary,
                                    textAlign = TextAlign.End
                                )
                            )

                            Icon(
                                modifier = Modifier
                                    .rotate(180f)
                                    .padding(horizontal = 4.dp),
                                painter = painterResource(R.drawable.ic_up_arrow),
                                contentDescription = "show more arrow",
                                tint = LocalColorBluePrimary
                            )
                        }
                    }
                }
            }

            if (
                !isNewsLoading.value && !isAttractionLoading.value &&
                news.isEmpty() && attractions.isEmpty()
            ) {
                item {
                    EmptyScreen(sizeInDp)
                }
            }
        }
    }
}



