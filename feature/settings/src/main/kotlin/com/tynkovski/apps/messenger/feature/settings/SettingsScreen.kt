package com.tynkovski.apps.messenger.feature.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.union
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun SettingsRoute(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    SettingsScreen(
        modifier = modifier,
        signOut = viewModel::signOut,
    )
}

@Composable
internal fun SettingsScreen(
    modifier: Modifier = Modifier,
    signOut: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Settings"
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = signOut
            ) {
                Text("sign out")
            }
        }
    }

}