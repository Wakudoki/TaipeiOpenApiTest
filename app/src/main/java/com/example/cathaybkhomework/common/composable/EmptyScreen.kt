package com.example.cathaybkhomework.common.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import com.example.cathaybkhomework.R

@Composable
fun EmptyScreen(
    sizeInDp: DpSize = DpSize.Zero
) {
    Box(
        modifier = Modifier.fillMaxSize().padding(top = sizeInDp.height/3)
    ) {
        Image(
            modifier = Modifier.align(Alignment.Center),
            painter = painterResource(R.drawable.ic_empty_content),
            contentDescription = "Empty Content",
            colorFilter = ColorFilter.tint(LocalColorIconNormal)
        )
    }
}