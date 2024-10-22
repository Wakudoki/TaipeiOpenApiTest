package com.example.cathaybkhomework.page.tours.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import com.example.cathaybkhomework.common.composable.LocalColorBlueSecondary
import com.example.cathaybkhomework.common.composable.LocalColorLine2
import com.example.cathaybkhomework.common.composable.LocalColorTextTitle
import com.example.cathaybkhomework.common.ext.toAnnotatedString
import com.example.cathaybkhomework.common.language.MyLanguage
import com.example.cathaybkhomework.data.TourItem
import com.example.cathaybkhomework.page.wabview.WebViewActivity
import com.example.myandroid.extension.clickableNoRipple

@Composable
fun ToursDetailScreen(
    modifier: Modifier = Modifier,
    tourItem: TourItem
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        tourItem.let {
            ToursInfo(it)

            ToursIntroduction(it)

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ToursInfo(
    tourItem: TourItem
) {
    val context = LocalContext.current
    val column1Weight = .25f
    val column2Weight = .75f
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        //適合季節
        if (tourItem.season?.isNotEmpty() == true) {
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
                    repeat(tourItem.season.size) {
                        Text(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(LocalColorLine2)
                                .padding(horizontal = 12.dp, vertical = 4.dp),
                            text = when (tourItem.season[it]) {
                                "01" -> MyLanguage.strings.spring
                                "02" -> MyLanguage.strings.summer
                                "03" -> MyLanguage.strings.autumn
                                "04" -> MyLanguage.strings.winter
                                else -> ""
                            },
                            style = TextStyle(
                                fontSize = 12.sp,
                                color = LocalColorTextTitle
                            )
                        )
                    }
                }
            }
        }

        //網址
        if (tourItem.url.isNotEmpty()) {
            Row(
                modifier = Modifier.clickableNoRipple {
                    context.startActivity(
                        WebViewActivity.newIntent(
                            context = context,
                            url = tourItem.url,
                            title = tourItem.title
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
                    text = tourItem.url,
                    style = TextStyle(
                        textDecoration = TextDecoration.Underline,
                        fontSize = 12.sp,
                        color = LocalColorBlueSecondary
                    ),
                )
            }
        }
    }
}

@Composable
private fun ToursIntroduction(
    tourItem: TourItem
) {
    Text(
        modifier = Modifier.padding(horizontal = 16.dp),
        text = HtmlCompat.fromHtml(tourItem.description, HtmlCompat.FROM_HTML_MODE_COMPACT)
            .toAnnotatedString()
            .trim()
            .toString(),
        style = TextStyle(
            fontSize = 14.sp,
            color = LocalColorTextTitle
        ),
    )
}