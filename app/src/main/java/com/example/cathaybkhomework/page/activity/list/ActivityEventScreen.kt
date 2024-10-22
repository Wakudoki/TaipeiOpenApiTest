package com.example.cathaybkhomework.page.activity.list

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
import androidx.compose.ui.graphics.Color
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
import com.example.cathaybkhomework.data.ActivityEventItem
import com.example.cathaybkhomework.page.activity.detail.ActivityEventDetailActivity
import com.example.cathaybkhomework.utils.TimeUtils
import com.example.myandroid.extension.clickableNoRipple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityEventScreen(
    viewModel: ActivityEventViewModel
) {
    val isRefreshing = viewModel.refreshingState.collectAsState()
    val isLoading = viewModel.loadingState.collectAsState()

    val activityEventData = viewModel.activityEvent.collectAsState()
    val activityEvent = activityEventData.value?.data ?: emptyList()

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
                    if (activityEvent.isEmpty()) {
                        item {
                            EmptyScreen(sizeInDp)
                        }
                    } else {
                        items(
                            count = activityEvent.size
                        ) { index ->
                            ActivityEventCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                activityEvent = activityEvent[index]
                            ) {
                                context.startActivity(
                                    ActivityEventDetailActivity.newIntent(
                                        context = context,
                                        title = it.title,
                                        activityEventItem = it
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
fun ActivityEventCard(
    modifier: Modifier = Modifier,
    activityEvent: ActivityEventItem,
    onClick: (activityEvent: ActivityEventItem) -> Unit
) {
    Row(
        modifier = modifier
            .border(width = 1.dp, color = LocalColorLine3)
            .padding(8.dp)
            .clickableNoRipple { onClick.invoke(activityEvent) },
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
                text = activityEvent.title,
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
            ) {
                Text(
                    text = TimeUtils.getDurationDate(
                        beginTimeString = activityEvent.begin,
                        endTimeString = activityEvent.end
                    ),
                    style = TextStyle(
                        fontSize = 12.sp,
                    ),
                    color = Color.Red.copy(alpha = .8f)
                )

                Spacer(modifier = Modifier.weight(1f))

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