package com.tynkovski.apps.messenger.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.tynkovski.apps.messenger.core.designsystem.theme.MessengerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessengerTopAppBar(
    @StringRes titleRes: Int,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
) {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(id = titleRes)) },
        colors = colors,
        modifier = modifier
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