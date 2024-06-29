package com.tynkovski.apps.messenger.core.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun GroupSetting(
    modifier: Modifier,
    main: @Composable () -> Unit,
    additional: (@Composable ColumnScope.() -> Unit)? = null,
) = Column(
    modifier = modifier,
) {
    main()

    additional?.let {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column {
                it()
            }
        }
    }
}