package com.example.cathaybkhomework.common.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextGrid(
    modifier: Modifier,
    text: String,
    color: Color,
    fontSize: TextUnit = 12.sp
) {
    Text(
        modifier = modifier
            .border(1.dp, color)
            .padding(8.dp)
            .wrapContentSize(align = Alignment.Center),
        text = text,
        style = TextStyle(
            color = color,
            fontWeight = FontWeight.Bold,
            fontSize = fontSize
        )
    )
}

@Preview
@Composable
fun NoImagePreview() {
    TextGrid(
        modifier = Modifier
            .width(100.dp)
            .height(80.dp),
        color = Color.LightGray,
        text = "No Image"
    )
}