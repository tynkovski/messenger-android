package com.tynkovski.apps.messenger.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.tynkovski.apps.messenger.core.designsystem.theme.MessengerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessengerTopAppBar(
    @StringRes titleRes: Int,
    modifier: Modifier = Modifier,
    navigation: @Composable () -> Unit = {},
    actions: @Composable() (RowScope.() -> Unit) = {},
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
) {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(id = titleRes)) },
        colors = colors,
        navigationIcon = navigation,
        modifier = modifier,
        actions = actions
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview("Top App Bar")
@Composable
private fun NiaTopAppBarPreview() {
    MessengerTheme {
        MessengerTopAppBar(
            titleRes = android.R.string.untitled
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview("Top App Bar", showBackground = true)
@Composable
private fun NiaTopAppBarPreview2() {
    MessengerTheme {
        MessengerTopAppBar(
            titleRes = android.R.string.untitled,
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = null
                    )
                }
            }
        )
    }
}