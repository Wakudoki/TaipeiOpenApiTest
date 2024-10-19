package com.example.myandroid.extension

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.semantics.Role
import com.example.cathaybkhomework.common.composable.LocalColorLine3

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.clickableRipple(
    interactionSource: MutableInteractionSource? = null,
    indication: Indication? = null,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
) = composed {
    Modifier.clickable(
        interactionSource = interactionSource ?: remember {
            MutableInteractionSource()
        },
        indication = indication //?: ripple(
//            color = LocalColorTextNormal.copy(alpha = 0.3f),
        //),
        ,
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role,
        onClick = onClick
    )
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.clickableNoRipple(
    interactionSource: MutableInteractionSource? = null,
    indication: Indication? = null,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
) = composed {
    Modifier.clickable(
        interactionSource = interactionSource ?: remember {
            MutableInteractionSource()
        },
        indication = indication,
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role,
        onClick = onClick
    )
}

@SuppressLint("SuspiciousModifierThen")
fun Modifier.shimmerBackground(shape: Shape = RectangleShape): Modifier = composed {
    val transition = rememberInfiniteTransition()

    val translateAnimation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 800f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 2500, easing = LinearOutSlowInEasing),
            RepeatMode.Restart
        ),
    )
    val shimmerColors = listOf(
        LocalColorLine3.copy(alpha = 0.8f),
        LocalColorLine3.copy(alpha = 0.3f),
    )
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnimation, translateAnimation),
        end = Offset(translateAnimation + 200f, translateAnimation + 200f),
        tileMode = TileMode.Mirror,
    )
    return@composed this.then(background(brush, shape))
}