package com.example.cathaybkhomework.page.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import com.example.cathaybkhomework.R
import com.example.cathaybkhomework.common.composable.EmptyScreen
import com.example.cathaybkhomework.common.composable.LoadingGradient
import com.example.cathaybkhomework.common.composable.LocalColorBackgroundOriginal
import com.example.cathaybkhomework.common.composable.LocalColorBluePrimary
import com.example.cathaybkhomework.common.composable.LocalColorLine3
import com.example.cathaybkhomework.common.composable.LocalColorTextSubtitle
import com.example.cathaybkhomework.common.composable.LocalColorTextTitle
import com.example.cathaybkhomework.common.ext.toAnnotatedString
import com.example.cathaybkhomework.common.language.MyLanguage
import com.example.cathaybkhomework.data.NewsItem
import com.example.myandroid.extension.clickableNoRipple
import kotlin.math.min

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
) {
    val newsData = viewModel.events.collectAsState()
    val news = newsData.value?.data ?: emptyList()
    val isLoading = viewModel.loadingState.collectAsState()
    val isRefreshing = viewModel.refreshingState.collectAsState()
    val newsDisplayCount = remember { mutableIntStateOf(3) }

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
        if (isLoading.value) {
            LoadingGradient()
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (news.isEmpty()) {
                    item {
                        EmptyScreen(sizeInDp)
                    }
                } else {
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
                        NewsCard(news[index])
                    }

                    item {
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
                                        fontSize = 10.sp,
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
            }
        }
    }
}

@Composable
fun NewsCard(news: NewsItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(width = 1.dp, color = LocalColorLine3)
            .padding(8.dp)
    ) {
        Text(
            text = news.title,
            style = TextStyle(
                fontSize = 16.sp,
                color = LocalColorTextTitle
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = news.modified,
            style = TextStyle(
                fontSize = 8.sp,
                color = LocalColorTextSubtitle
            )
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = HtmlCompat.fromHtml(news.description, HtmlCompat.FROM_HTML_MODE_COMPACT)
                .toAnnotatedString()
                .trim()
                .toString(),
            style = TextStyle(
                fontSize = 12.sp,
                color = LocalColorTextTitle,
            ),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}