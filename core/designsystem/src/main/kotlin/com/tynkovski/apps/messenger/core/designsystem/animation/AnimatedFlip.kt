package com.tynkovski.apps.messenger.core.designsystem.animation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun AnimatedFlip(
    flip: Boolean,
    face: @Composable (Modifier) -> Unit,
    back: @Composable (Modifier) -> Unit,
) {
    val rotation by animateFloatAsState(
        targetValue = if (flip) 180f else 0f,
        animationSpec = tween(
            durationMillis = 400,
            easing = FastOutSlowInEasing,
        )
    )
    if (rotation <= 90f) {
        face(Modifier.graphicsLayer { rotationY = rotation })
    } else {
        back(Modifier.graphicsLayer { rotationY = 180f - rotation })
    }
}