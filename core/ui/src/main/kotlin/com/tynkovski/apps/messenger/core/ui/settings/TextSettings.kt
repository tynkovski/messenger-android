package com.tynkovski.apps.messenger.core.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tynkovski.apps.messenger.core.designsystem.helpers.ifTrue

@Composable
fun TextSetting(
    modifier: Modifier,
    title: String,
    description: String? = null,
    padding: PaddingValues = PaddingValues(horizontal = 16.dp),
    titlePadding: PaddingValues = PaddingValues(0.dp),
    onClick: (() -> Unit)? = null,
    content: @Composable RowScope. () -> Unit,
) = Row(
    modifier = modifier
        .heightIn(min = 48.dp)
        .ifTrue(onClick != null) { clickable(onClick = onClick!!) }
        .padding(padding),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
) {
    Column {
        Text(
            modifier = Modifier.padding(titlePadding),
            style = MaterialTheme.typography.bodyMedium,
            text = title,
            maxLines = 1
        )
        description?.let {
            Text(
                style = MaterialTheme.typography.labelMedium,
                text = it,
                maxLines = 1
            )
        }
    }
    Spacer(modifier = Modifier.width(12.dp))
    content()
}