package com.example.cathaybkhomework.page.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cathaybkhomework.common.composable.EmptyScreen
import com.example.cathaybkhomework.common.composable.LoadingGradient
import com.example.cathaybkhomework.common.composable.LocalColorBackgroundOriginal
import com.example.cathaybkhomework.common.composable.LocalColorBackgroundSecondary
import com.example.cathaybkhomework.common.composable.LocalColorBluePrimary
import com.example.cathaybkhomework.common.composable.LocalColorLine3
import com.example.cathaybkhomework.common.composable.LocalColorTextTitle
import com.example.cathaybkhomework.common.language.MyLanguage
import com.example.cathaybkhomework.data.ActivityEventItem
import com.example.cathaybkhomework.data.EventCalendarItem
import com.example.cathaybkhomework.page.activity.detail.ActivityEventDetailActivity
import com.example.cathaybkhomework.utils.TimeUtils
import com.example.myandroid.extension.clickableNoRipple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventCalendarScreen(
    viewModel: EventCalendarViewModel
) {
    val isRefreshing = viewModel.refreshingState.collectAsState()
    val isLoading = viewModel.loadingState.collectAsState()

    val eventCalendarData = viewModel.eventCalendar.collectAsState()
    val eventCalendar = eventCalendarData.value?.data ?: emptyList()

    val monthData = mutableMapOf<String, List<EventCalendarItem>>()
    eventCalendar.forEach {
        val month = TimeUtils.parseTime(timeString = it.begin, outputFormat = TimeUtils.yyyyMM)
        monthData[month] = monthData.getOrDefault(month, emptyList()) + it
    }

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
                    if (eventCalendar.isEmpty()) {
                        item {
                            EmptyScreen(sizeInDp)
                        }
                    } else {
                        items(
                            count = monthData.size
                        ) { index ->
                            val month = monthData.keys.toList()[index]
                            EventCalendarMonthList(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                month = month,
                                list = monthData[month] ?: emptyList()
                            ) {
                                context.startActivity(
                                    ActivityEventDetailActivity.newIntent(
                                        context = context,
                                        title = it.title,
                                        activityEventItem = it.let { item ->
                                            ActivityEventItem(
                                                id = item.id,
                                                contact = "",
                                                tel = item.tel,
                                                title = item.title,
                                                description = item.description,
                                                begin = item.begin,
                                                end = item.end,
                                                posted = item.posted,
                                                modified = item.modified,
                                                url = item.url,
                                                links = item.links
                                            )
                                        }
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
fun EventCalendarMonthList(
    modifier: Modifier = Modifier,
    month: String,
    list: List<EventCalendarItem>,
    onClick: (eventCalendarItem: EventCalendarItem) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = month,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = LocalColorTextTitle
            ),
        )

        repeat(list.size) {
            EventCalendarCard(
                eventCalendarItem = list[it],
                onClick = onClick
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun EventCalendarCard(
    modifier: Modifier = Modifier,
    eventCalendarItem: EventCalendarItem,
    onClick: (eventCalendarItem: EventCalendarItem) -> Unit
) {
    Box {
        Row(
            modifier = modifier
                .padding(top = 12.dp)
                .border(width = 1.dp, color = LocalColorLine3)
                .padding(8.dp)
                .clickableNoRipple { onClick.invoke(eventCalendarItem) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(LocalColorBackgroundSecondary)
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 4.dp)
            ) {
                Text(
                    text = eventCalendarItem.title,
                    style = TextStyle(
                        fontSize = 20.sp
                    ),
                    color = LocalColorTextTitle
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = TimeUtils.getDurationDate(
                        beginTimeString = eventCalendarItem.begin,
                        endTimeString = eventCalendarItem.end
                    ),
                    style = TextStyle(
                        fontSize = 14.sp,
                    ),
                    color = LocalColorBluePrimary
                )
            }

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "view detail"
            )
        }

        if (eventCalendarItem.is_major) {
            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Red)
                    .padding(horizontal = 12.dp, vertical = 4.dp)
                    .align(Alignment.TopStart),
                text = MyLanguage.strings.recommend,
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color.White
                )
            )
        }
    }
}