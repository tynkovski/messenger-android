package com.tynkovski.apps.messenger.core.ui.message

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MessageBubbleContent(
    text: @Composable () -> Unit,
    indicators: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
) = FlowRow(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.End),
    verticalArrangement = Arrangement.Bottom
) {
    text()
    Row(
        modifier = Modifier
            .align(Alignment.Bottom)
            .padding(bottom = 1.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        indicators()
    }
}
