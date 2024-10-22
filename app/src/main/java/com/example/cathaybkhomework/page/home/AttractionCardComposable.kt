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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AttractionCard(
    modifier: Modifier = Modifier,
    attraction: AttractionItem,
    onClick: (attraction: AttractionItem) -> Unit
) {
    Row(
        modifier = modifier
            .background(LocalColorBackgroundSecondary)
            .border(width = 1.dp, color = LocalColorLine3)
            .padding(8.dp)
            .clickableNoRipple { onClick.invoke(attraction) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (attraction.images.isEmpty()) {
            TextGrid(   //無圖片顯示
                modifier = Modifier
                    .width(100.dp)
                    .height(80.dp),
                color = LocalColorLine3,
                text = MyLanguage.strings.noImage
            )
        } else {
            GlideImage(
                modifier = Modifier
                    .width(100.dp)
                    .height(80.dp),
                model = attraction.images.first().src,
                contentDescription = "Attraction Image",
                contentScale = ContentScale.Crop,
//                    loading = placeholder(R.drawable.ic_image_placehold), //這邊Glide似乎有bug，設定loading placeholder後未滑動前圖片出不來
                failure = placeholder(R.drawable.ic_image_error_placehold),
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .height(80.dp)
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