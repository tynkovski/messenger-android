package com.tynkovski.apps.messenger.core.ui.message

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tynkovski.apps.messenger.core.designsystem.component.ReversedRow

@Composable
fun MessageBubbleBase(
    modifier: Modifier = Modifier,
    owner: Boolean,
    bias: Float,
    readIndicator: (@Composable () -> Unit)?,
    message: @Composable RowScope.() -> Unit,
) = ReversedRow(
    reversed = owner,
    modifier = modifier,
    horizontalArrangement = if (owner) Arrangement.End else Arrangement.Start,
    verticalAlignment = Alignment.Bottom,
    content = listOf(
        message,
        { Spacer(modifier = Modifier.width(2.dp)) },
        {
            Row(
                modifier = Modifier.weight(1f - bias),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 2.dp,
                    alignment = if (owner) Alignment.End else Alignment.Start
                )
            ) {
                readIndicator?.invoke()
            }
        }
    )
)