package com.tynkovski.apps.messenger.feature.contacts

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun ContactsRoute(
    modifier: Modifier = Modifier,
    viewModel: ContactsViewModel = hiltViewModel(),
) {
    ContactsScreen(modifier = modifier)
}

@Composable
internal fun ContactsScreen(
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = Modifier.fillMaxSize(),
        text = "Contacts"
    )
}