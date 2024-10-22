package com.example.cathaybkhomework.common.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.sp
import com.example.cathaybkhomework.R
import com.example.cathaybkhomework.common.language.MyLanguage

@Composable
fun EmptyScreen(
    sizeInDp: DpSize = DpSize.Zero
) {
    Box(
        modifier = Modifier.fillMaxSize().padding(top = sizeInDp.height/3)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.ic_empty_content),
                contentDescription = "Empty Content",
                colorFilter = ColorFilter.tint(LocalColorIconNormal)
            )

            Text(
                text = MyLanguage.strings.emptyContent,
                style = TextStyle(
                    fontSize = 24.sp,
                    color = LocalColorIconNormal
                )
            )
        }
    }
}