package com.tynkovski.apps.messenger.core.ui.message

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

data class MessageStyle(
    val bubbleColor: Color,
    val indicatorColor: Color,
    val textColor: Color,
    val dateColor: Color,
    val textStyle: TextStyle,
    val dateStyle: TextStyle
)

@Composable
fun messageDefaults(
    bubbleColor: Color = MaterialTheme.colorScheme.inversePrimary,
    indicatorColor: Color = MaterialTheme.colorScheme.inversePrimary,
    textColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    dateColor: Color =  MaterialTheme.colorScheme.onSecondaryContainer,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge.copy(lineHeight = 20.sp),
    dateStyle: TextStyle = MaterialTheme.typography.labelMedium.copy(lineHeight = 16.sp),
) = MessageStyle(
    bubbleColor = bubbleColor,
    indicatorColor = indicatorColor,
    textColor = textColor,
    dateColor = dateColor,
    textStyle = textStyle,
    dateStyle = dateStyle
)