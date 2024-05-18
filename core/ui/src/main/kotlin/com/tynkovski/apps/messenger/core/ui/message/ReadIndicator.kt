package com.tynkovski.apps.messenger.core.ui.message

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ReadIndicator(color: Color) = Spacer(
    modifier = Modifier
        .size(8.dp)
        .background(
            color = color,
            shape = CircleShape,
        )
)