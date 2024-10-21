package com.example.cathaybkhomework.page.attraction.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.cathaybkhomework.R
import com.example.cathaybkhomework.common.composable.LocalColorBluePrimary
import com.example.cathaybkhomework.common.composable.LocalColorBlueSecondary
import com.example.cathaybkhomework.common.composable.LocalColorLine2
import com.example.cathaybkhomework.common.composable.LocalColorTextTitle
import com.example.cathaybkhomework.common.language.MyLanguage
import com.example.cathaybkhomework.data.AttractionItem
import com.example.cathaybkhomework.page.wabview.WebViewActivity
import com.example.myandroid.extension.clickableNoRipple

@Composable
fun AttractionDetailScreen(
    modifier: Modifier = Modifier,
    attractionItem: AttractionItem
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        attractionItem.let {
            AttractionImages(it)

            AttractionInfo(it)

            AttractionIntroduction(it)

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun AttractionImages(
    attractionItem: AttractionItem
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val pagerState = rememberPagerState(pageCount = { attractionItem.images.size })
    Box {
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth(),
            state = pagerState
        ) { page ->
            Box(
                modifier = Modifier
                    .width(screenWidth)
                    .height(screenWidth / 1.2f)
            ) {
                GlideImage(
                    model = attractionItem.images[page].src,
                    contentDescription = "Attraction Image",
                    contentScale = ContentScale.Crop,
//                    loading = placeholder(R.drawable.ic_image_placehold), //這邊Glide似乎有bug，設定loading placeholder後未滑動前圖片出不來
                    failure = placeholder(R.drawable.ic_image_error_placehold),
                )
            }
        }

        //indicator
        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) Color.White else Color.White.copy(
                        alpha = 0.5f
                    )
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(8.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AttractionInfo(
    attractionItem: AttractionItem
) {
    val context = LocalContext.current
    val column1Weight = .25f
    val column2Weight = .75f
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        //營業時間
        if (attractionItem.open_time.isNotEmpty()) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(column1Weight),
                    text = MyLanguage.strings.businessHours + "：",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = LocalColorTextTitle
                    ),
                )

                Text(
                    modifier = Modifier.weight(column2Weight),
                    text = attractionItem.open_time,
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = LocalColorTextTitle
                    ),
                )
            }
        }

        //地址
        if (attractionItem.address.isNotEmpty()) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(column1Weight),
                    text = MyLanguage.strings.address + "：",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = LocalColorTextTitle
                    ),
                )

                Text(
                    modifier = Modifier.weight(column2Weight),
                    text = attractionItem.address,
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = LocalColorTextTitle
                    ),
                )
            }
        }

        //連絡電話
        if (attractionItem.tel.isNotEmpty()) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(column1Weight),
                    text = MyLanguage.strings.tel + "：",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = LocalColorTextTitle
                    ),
                )

                Text(
                    modifier = Modifier.weight(column2Weight),
                    text = attractionItem.tel,
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = LocalColorTextTitle
                    ),
                )
            }
        }

        //網址
        if (attractionItem.url.isNotEmpty()) {
            Row(
                modifier = Modifier.clickableNoRipple {
                    context.startActivity(
                        WebViewActivity.newIntent(
                            context = context,
                            url = attractionItem.url,
                            title = attractionItem.name
                        )
                    )
                },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(column1Weight),
                    text = MyLanguage.strings.url + "：",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = LocalColorTextTitle
                    ),
                )

                Text(
                    modifier = Modifier.weight(column2Weight),
                    text = attractionItem.url,
                    style = TextStyle(
                        textDecoration = TextDecoration.Underline,
                        fontSize = 12.sp,
                        color = LocalColorBlueSecondary
                    ),
                )
            }
        }

        //分類
        if (attractionItem.category.isNotEmpty()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.weight(column1Weight),
                    text = MyLanguage.strings.category + "：",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = LocalColorTextTitle
                    ),
                )

                FlowRow(
                    modifier = Modifier.weight(column2Weight),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    repeat(attractionItem.category.size) {
                        Text(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(LocalColorLine2)
                                .padding(horizontal = 12.dp, vertical = 4.dp),
                            text = attractionItem.category[it].name,
                            style = TextStyle(
                                fontSize = 12.sp,
                                color = LocalColorTextTitle
                            )
                        )
                    }
                }
            }
        }

        //服務
        if (attractionItem.service.isNotEmpty()) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(column1Weight),
                    text = MyLanguage.strings.serviceFacilities + "：",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = LocalColorTextTitle
                    ),
                )

                FlowRow(
                    modifier = Modifier.weight(column2Weight),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    repeat(attractionItem.service.size) {
                        Row {
                            Icon(
                                modifier = Modifier.size(16.dp),
                                painter = painterResource(R.drawable.ic_check),
                                contentDescription = "",
                                tint = LocalColorBluePrimary
                            )

                            Text(
                                text = attractionItem.service[it].name,
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    color = LocalColorTextTitle
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AttractionIntroduction(
    attractionItem: AttractionItem
) {
    Text(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 12.dp),
        text = MyLanguage.strings.attractionIntroduction,
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = LocalColorTextTitle
        ),
    )

    Text(
        modifier = Modifier.padding(horizontal = 16.dp),
        text = attractionItem.introduction,
        style = TextStyle(
            fontSize = 14.sp,
            color = LocalColorTextTitle
        ),
    )
}