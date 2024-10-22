package com.example.cathaybkhomework.page.activity.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import com.example.cathaybkhomework.common.composable.LocalColorBluePrimary
import com.example.cathaybkhomework.common.composable.LocalColorBlueSecondary
import com.example.cathaybkhomework.common.composable.LocalColorLine3
import com.example.cathaybkhomework.common.composable.LocalColorTextSubtitle
import com.example.cathaybkhomework.common.composable.LocalColorTextTitle
import com.example.cathaybkhomework.common.ext.toAnnotatedString
import com.example.cathaybkhomework.common.language.MyLanguage
import com.example.cathaybkhomework.data.ActivityEventItem
import com.example.cathaybkhomework.page.wabview.WebViewActivity
import com.example.cathaybkhomework.utils.TimeUtils
import com.example.myandroid.extension.clickableNoRipple

@Composable
fun ActivityEventDetailScreen(
    modifier: Modifier = Modifier,
    activityEventItem: ActivityEventItem
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        activityEventItem.let {
            Spacer(modifier = Modifier.height(12.dp))

            ActivityEventInfo(it)

            ActivityEventIntroduction(it)

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ActivityEventInfo(
    activityEventItem: ActivityEventItem
) {
    val context = LocalContext.current
    val column1Weight = .25f
    val column2Weight = .75f
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        //活動時間
        if (activityEventItem.begin.isNotEmpty() && activityEventItem.end.isNotEmpty()) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(column1Weight),
                    text = MyLanguage.strings.eventTime + "：",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = LocalColorTextTitle
                    ),
                )

                Text(
                    modifier = Modifier.weight(column2Weight),
                    text = TimeUtils.parseTime(
                        timeString = activityEventItem.begin,
                        outputFormat = TimeUtils.yyyyMMdd
                    ) + "~" +
                            TimeUtils.parseTime(
                                timeString = activityEventItem.end,
                                outputFormat = TimeUtils.yyyyMMdd
                            ),
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = LocalColorTextTitle
                    ),
                )
            }
        }

        //連絡電話
        if (activityEventItem.tel.isNotEmpty()) {
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
                    text = activityEventItem.tel,
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = LocalColorTextTitle
                    ),
                )
            }
        }

        //網址
        if (activityEventItem.url.isNotEmpty()) {
            Row(
                modifier = Modifier.clickableNoRipple {
                    context.startActivity(
                        WebViewActivity.newIntent(
                            context = context,
                            url = activityEventItem.url,
                            title = activityEventItem.title
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
                    text = activityEventItem.url,
                    style = TextStyle(
                        textDecoration = TextDecoration.Underline,
                        fontSize = 12.sp,
                        color = LocalColorBlueSecondary
                    ),
                )
            }
        }

        //相關連結
        if (activityEventItem.links.isNotEmpty()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.weight(column1Weight),
                    text = MyLanguage.strings.links + "：",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = LocalColorTextTitle
                    ),
                )

                FlowColumn(
                    modifier = Modifier.weight(column2Weight),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    repeat(activityEventItem.links.size) {
                        Row(
                            modifier = Modifier
                                .border(1.dp, LocalColorLine3)
                                .padding(6.dp)
                                .clickableNoRipple {
                                    context.startActivity(
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse(activityEventItem.links[it].src)
                                        )
                                    )
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.size(16.dp),
                                imageVector = Icons.Filled.Send,
                                contentDescription = "",
                                tint = LocalColorBluePrimary
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = activityEventItem.links[it].subject,
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

        //發佈日期
        if (activityEventItem.posted.isNotEmpty()) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(column1Weight),
                    text = MyLanguage.strings.postedDate + "：",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = LocalColorTextTitle
                    ),
                )

                Text(
                    modifier = Modifier.weight(column2Weight),
                    text = TimeUtils.parseTime(
                        timeString = activityEventItem.posted,
                        outputFormat = TimeUtils.yyyyMMdd
                    ),
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = LocalColorTextSubtitle
                    ),
                )
            }
        }

        //更新日期
        if (activityEventItem.modified.isNotEmpty()) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(column1Weight),
                    text = MyLanguage.strings.modifiedDate + "：",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = LocalColorTextTitle
                    ),
                )

                Text(
                    modifier = Modifier.weight(column2Weight),
                    text = TimeUtils.parseTime(
                        timeString = activityEventItem.modified,
                        outputFormat = TimeUtils.yyyyMMdd
                    ),
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = LocalColorTextSubtitle
                    ),
                )
            }
        }
    }
}

@Composable
private fun ActivityEventIntroduction(
    activityEventItem: ActivityEventItem
) {
    Text(
        modifier = Modifier.padding(horizontal = 16.dp),
        text = HtmlCompat.fromHtml(activityEventItem.description, HtmlCompat.FROM_HTML_MODE_COMPACT)
            .toAnnotatedString()
            .trim()
            .toString(),
        style = TextStyle(
            fontSize = 14.sp,
            color = LocalColorTextTitle
        ),
    )
}