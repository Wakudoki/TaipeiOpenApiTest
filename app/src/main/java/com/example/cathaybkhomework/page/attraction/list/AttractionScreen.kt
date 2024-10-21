package com.example.cathaybkhomework.page.attraction.list

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.cathaybkhomework.R
import com.example.cathaybkhomework.common.composable.LoadingGradient
import com.example.cathaybkhomework.common.composable.LocalColorBackgroundOriginal
import com.example.cathaybkhomework.common.composable.LocalColorBackgroundSecondary
import com.example.cathaybkhomework.common.composable.LocalColorBluePrimary
import com.example.cathaybkhomework.common.composable.LocalColorBlueSecondary
import com.example.cathaybkhomework.common.composable.LocalColorTextSubtitle
import com.example.cathaybkhomework.common.composable.LocalColorTextTitle
import com.example.cathaybkhomework.common.language.MyLanguage
import com.example.cathaybkhomework.data.CategoryDetail
import com.example.cathaybkhomework.page.attraction.detail.AttractionDetailActivity
import com.example.cathaybkhomework.page.home.AttractionCard
import com.example.myandroid.extension.clickableNoRipple

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AttractionScreen(
    viewModel: AttractionViewModel
) {
    val isRefreshing = viewModel.refreshingState.collectAsState()
    val isLoading = viewModel.loadingState.collectAsState()

    val categoriesData = viewModel.categories.collectAsState()
    val categories = categoriesData.value?.data?.category ?: emptyList()

    val attractionsData = viewModel.attractions.collectAsState()
    val attractions = attractionsData.value?.data ?: emptyList()

    var menuState by remember { mutableStateOf(false) }
    val selectedCategories = viewModel.selectedCategories.collectAsState()

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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickableNoRipple {
                        menuState = true
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    modifier = Modifier
                        .size(32.dp)
                        .weight(1f),
                    painter = painterResource(R.drawable.ic_filter),
                    contentDescription = "Filter",
                    tint = LocalColorBluePrimary
                )
            }

            if (isLoading.value) {
                LoadingGradient(
                    count = 10,
                    height = 80.dp
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        count = attractions.size
                    ) { index ->
                        AttractionCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
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
                }
            }
        }

        if (menuState) {
            CategoryDialog(
                categories = categories,
                selectedCategories = selectedCategories.value.map { it.id },
                onSelected = {
                    viewModel.setSelectedCategories(it)
                },
            ) {
                menuState = false
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
private fun CategoryDialog(
    categories: List<CategoryDetail>,
    selectedCategories: List<String>,
    onSelected: (categories: List<CategoryDetail>) -> Unit,
    onDismissRequest: () -> Unit
) {
    var selectedCategoryMap by remember {
        mutableStateOf(
            categories.associateWith {
                //沒有被選擇的類型，表示全選
                if (selectedCategories.isEmpty()) {
                    true
                } else {
                    it.id in selectedCategories
                }
            }
        )
    }

    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier.background(LocalColorBackgroundOriginal)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(80.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(
                    count = categories.size
                ) { index ->
                    Text(
                        modifier = Modifier
                            .height(60.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(
                                if (selectedCategoryMap[categories[index]] == true) {
                                    LocalColorBlueSecondary
                                } else {
                                    LocalColorBackgroundSecondary
                                }
                            )
                            .padding(8.dp)
                            .clickableNoRipple {
                                selectedCategoryMap = selectedCategoryMap.toMutableMap().apply {
                                    this[categories[index]] =
                                        selectedCategoryMap[categories[index]] != true
                                }
                            }
                            .wrapContentSize(align = Alignment.Center),
                        text = categories[index].name,
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp,
                            color = if (selectedCategoryMap[categories[index]] == true) {
                                Color.White
                            } else {
                                LocalColorTextTitle
                            }
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(LocalColorBackgroundSecondary)
                        .padding(8.dp)
                        .weight(.5f)
                        .clickableNoRipple {
                            selectedCategoryMap = categories.associateWith { false }
                        },
                    text = MyLanguage.strings.clearAll,
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp,
                        color = LocalColorTextSubtitle
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .fillMaxSize()
                        .background(LocalColorBlueSecondary)
                        .padding(4.dp)
                        .weight(.5f)
                        .clickableNoRipple {
                            onSelected(
                                categories.filter { selectedCategoryMap[it] == true }
                            )
                            onDismissRequest.invoke()
                        },
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = Color.White
                )
            }
        }
    }
}