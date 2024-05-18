package com.tynkovski.apps.messenger.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ReversedRow(
    reversed: Boolean,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    content: List<@Composable RowScope.() -> Unit>,
) = Row(
    modifier = modifier,
    horizontalArrangement = horizontalArrangement,
    verticalAlignment = verticalAlignment,
) {
    val rowContent = remember(reversed, content) {
        movableContentOf {
            if (reversed) {
                content.reversed().forEach { it.invoke(this@Row) }
            } else {
                content.forEach { it.invoke(this@Row) }
            }
        }
    }
    rowContent.invoke()
}