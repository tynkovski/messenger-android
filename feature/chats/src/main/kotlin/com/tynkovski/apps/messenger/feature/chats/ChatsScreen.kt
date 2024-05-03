package com.tynkovski.apps.messenger.feature.chats

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun ChatsRoute(
    modifier: Modifier = Modifier,
    viewModel: ChatsViewModel = hiltViewModel(),
) {
    ChatsScreen(
        modifier = modifier
    )
}

@Composable
internal fun ChatsScreen(
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = Modifier,
        text = "Chats"
    )
}