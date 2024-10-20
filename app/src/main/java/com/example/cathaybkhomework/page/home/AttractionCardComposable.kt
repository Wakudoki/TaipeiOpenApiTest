package com.example.cathaybkhomework.page.home

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.cathaybkhomework.R
import com.example.cathaybkhomework.common.composable.LocalColorBackgroundSecondary
import com.example.cathaybkhomework.common.composable.LocalColorBluePrimary
import com.example.cathaybkhomework.common.composable.LocalColorLine3
import com.example.cathaybkhomework.common.composable.LocalColorTextSubtitle
import com.example.cathaybkhomework.common.composable.LocalColorTextTitle
import com.example.cathaybkhomework.common.composable.TextGrid
import com.example.cathaybkhomework.common.language.MyLanguage
import com.example.cathaybkhomework.data.AttractionItem
import com.example.myandroid.extension.clickableNoRipple
import kotlin.math.min

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AttractionCard(
    attraction: AttractionItem,
    onClick: (attraction: AttractionItem) -> Unit
) {
    val imagePageCount = min(attraction.images.size, 10)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(width = 1.dp, color = LocalColorLine3)
            .padding(8.dp)
            .clickableNoRipple { onClick.invoke(attraction) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (imagePageCount == 0) {
            TextGrid(   //無圖片顯示
                modifier = Modifier
                    .width(100.dp)
                    .height(80.dp),
                color = LocalColorLine3,
                text = MyLanguage.strings.noImage
            )
        } else {
            HorizontalPager(
                modifier = Modifier
                    .width(100.dp)
                    .height(80.dp),
                state = rememberPagerState(pageCount = { imagePageCount })
            ) { page ->
                Box(modifier = Modifier.fillMaxSize()) {
                    GlideImage(
                        model = attraction.images[page].src,
                        contentDescription = "Attraction Image",
                        contentScale = ContentScale.Crop,
//                    loading = placeholder(R.drawable.ic_image_placehold), //這邊Glide似乎有bug，設定loading placeholder後未滑動前圖片出不來
                        failure = placeholder(R.drawable.ic_image_error_placehold),
                    )

                    //首張圖片不顯示向左箭頭
                    if (page != 0) {
                        Icon(
                            modifier = Modifier.align(Alignment.CenterStart),
                            imageVector = Icons.Filled.KeyboardArrowLeft,
                            contentDescription = "Prev",
                            tint = Color.White
                        )
                    }

                    //末張圖片不顯示向右箭頭
                    if (page != imagePageCount - 1) {
                        Icon(
                            modifier = Modifier.align(Alignment.CenterEnd),
                            imageVector = Icons.Filled.KeyboardArrowRight,
                            contentDescription = "Prev",
                            tint = Color.White
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .height(80.dp)
                .background(LocalColorBackgroundSecondary)
                .padding(horizontal = 8.dp)
                .padding(bottom = 4.dp)
        ) {
            Text(
                text = attraction.name,
                style = TextStyle(
                    fontSize = 16.sp
                ),
                color = LocalColorTextTitle
            )

            Text(
                text = attraction.address,
                style = TextStyle(
                    fontSize = 12.sp,
                ),
                maxLines = 1,
                color = LocalColorTextSubtitle
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
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