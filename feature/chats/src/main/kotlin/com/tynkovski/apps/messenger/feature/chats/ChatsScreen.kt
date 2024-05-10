package com.tynkovski.apps.messenger.feature.chats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tynkovski.apps.messenger.core.model.Result
import com.tynkovski.apps.messenger.core.model.data.Room

@Composable
internal fun ChatsRoute(
    navigateToChat: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ChatsViewModel = hiltViewModel(),
) {
    val rooms by viewModel.rooms.collectAsStateWithLifecycle()
    ChatsScreen(
        state = rooms,
        modifier = modifier,
    )
}

@Composable
internal fun ChatsScreen(
    state: Result<List<Room>>,
    modifier: Modifier = Modifier,
) {
    when (state) {
        is Result.Error -> {
            Text(text = "Error")
        }

        Result.Loading -> {
            Text(text = "Loading")
        }

        is Result.Success -> {
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                for(chat in state.data) {
                    Chat(title = chat.name.toString(), action = chat.lastAction.toString())
                }
            }
        }
    }
}

@Composable
private fun Chat(
    title: String,
    action: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(modifier = Modifier.fillMaxWidth(), text = title)
        Spacer(modifier = Modifier.width(2.dp))
        Text(modifier = Modifier.fillMaxWidth(), text = action)
    }
}