package com.tynkovski.apps.messenger.feature.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun SettingsRoute(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    SettingsScreen(modifier = modifier)
}

@Composable
internal fun SettingsScreen(
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = Modifier.fillMaxSize(),
        text = "Settings"
    )
}