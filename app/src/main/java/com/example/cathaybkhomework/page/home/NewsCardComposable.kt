package com.example.cathaybkhomework.page.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import com.example.cathaybkhomework.common.composable.LocalColorLine3
import com.example.cathaybkhomework.common.composable.LocalColorTextSubtitle
import com.example.cathaybkhomework.common.composable.LocalColorTextTitle
import com.example.cathaybkhomework.common.ext.toAnnotatedString
import com.example.cathaybkhomework.data.NewsItem

@Composable
fun NewsCard(news: NewsItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(4.dp))
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