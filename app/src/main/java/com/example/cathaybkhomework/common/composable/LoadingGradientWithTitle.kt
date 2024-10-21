package com.example.cathaybkhomework.common.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.myandroid.extension.shimmerBackground

@Composable
fun LoadingGradientWithTitle(
    count: Int = 3
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .width(96.dp)
                .height(36.dp)
                .shimmerBackground(RoundedCornerShape(4.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))

        LoadingGradient(count)
    }
}

@Composable
fun LoadingGradient(
    count: Int = 3,
    height: Dp = 110.dp
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(count) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height)
                    .shimmerBackground(RoundedCornerShape(4.dp))
            )
        }
    }
}