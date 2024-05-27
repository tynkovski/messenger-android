package com.tynkovski.apps.messenger.core.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun ImageSettingsItem(
    text: String,
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
    tint: Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit = {}
) = Row(
    modifier = modifier
        .clickable(onClick = onClick)
        .padding(padding),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(12.dp)
) {
    Icon(
        imageVector = imageVector,
        contentDescription = null,
        tint = tint
    )
    Text(
        modifier = Modifier.weight(1f),
        style = MaterialTheme.typography.bodyMedium,
        text = text,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
}