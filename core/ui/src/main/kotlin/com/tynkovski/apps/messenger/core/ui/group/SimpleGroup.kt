package com.tynkovski.apps.messenger.core.ui.group

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SimpleGroup(
    title: String,
    modifier: Modifier = Modifier,
    spaceBetween: Dp = 0.dp,
    padding: PaddingValues = PaddingValues(horizontal = 0.dp),
    content: @Composable ColumnScope.() -> Unit
) = Column(modifier = modifier) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 10.dp),
        style = MaterialTheme.typography.titleSmall,
        text = title,
        textAlign = TextAlign.Center
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding),
        verticalArrangement = Arrangement.spacedBy(spaceBetween)
    ) {
        content.invoke(this)
    }
}