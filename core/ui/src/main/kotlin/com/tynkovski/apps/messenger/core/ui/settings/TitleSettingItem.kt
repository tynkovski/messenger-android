package com.tynkovski.apps.messenger.core.ui.settings


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun TitleSettingsItem(
    text: String,
    title: String,
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
    maxLinesContent: Int = 1,
    onClick: () -> Unit = {},
) = Column(
    modifier = modifier
        .clickable(onClick = onClick)
        .padding(padding),
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.labelMedium,
        text = title,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
    Text(
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Normal),
        text = text,
        overflow = TextOverflow.Ellipsis,
        maxLines = maxLinesContent
    )
}