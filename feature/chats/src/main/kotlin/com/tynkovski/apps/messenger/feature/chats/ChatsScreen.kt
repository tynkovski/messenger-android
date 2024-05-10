package com.tynkovski.apps.messenger.feature.chats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tynkovski.apps.messenger.core.designsystem.component.DefaultAvatar
import com.tynkovski.apps.messenger.core.designsystem.component.DefaultButton
import com.tynkovski.apps.messenger.core.designsystem.theme.MessengerTheme
import com.tynkovski.apps.messenger.core.model.Result
import com.tynkovski.apps.messenger.core.ui.bold
import com.tynkovski.apps.messenger.core.ui.semiBold

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
    state: Result<List<RoomUi>>,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        when (state) {
            is Result.Error -> {
                Error(
                    modifier = Modifier.fillMaxSize(),
                    error = state.exception.message.toString(),
                    onRetry = { }
                )
            }

            Result.Loading -> {
                Loading(modifier = Modifier.fillMaxSize())
            }

            is Result.Success -> {
                Success(
                    modifier = Modifier.fillMaxSize(),
                    chats = state.data,
                )
            }
        }
    }
}


private fun getChatActionDescription(action: RoomUi.LastActionUi.ActionTypeUi): Int {
    return when (action) {
        RoomUi.LastActionUi.ActionTypeUi.USER_CREATE_ROOM -> R.string.feature_chats_action_user_create_room
        RoomUi.LastActionUi.ActionTypeUi.USER_RENAME_ROOM -> R.string.feature_chats_action_user_rename_room
        RoomUi.LastActionUi.ActionTypeUi.USER_SENT_MESSAGE -> R.string.feature_chats_action_user_sent_message
        RoomUi.LastActionUi.ActionTypeUi.USER_INVITE_USER -> R.string.feature_chats_action_user_invite_user
        RoomUi.LastActionUi.ActionTypeUi.USER_KICK_USER -> R.string.feature_chats_action_user_kick_user
        RoomUi.LastActionUi.ActionTypeUi.USER_QUIT -> R.string.feature_chats_action_user_quit
        RoomUi.LastActionUi.ActionTypeUi.USER_JOINED -> R.string.feature_chats_action_user_joined
        RoomUi.LastActionUi.ActionTypeUi.MAKE_MODERATOR -> R.string.feature_chats_action_make_moderator
    }
}

@Composable
private fun Success(
    chats: List<RoomUi>,
    modifier: Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(chats.size) {
            val chat = chats[it]
            Chat(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                title = chat.name ?: "Without name",
                time = chat.lastAction.actionDateTime,
                actionType = chat.lastAction.actionType,
                author = chat.lastAction.authorName,
                description = chat.lastAction.description,
                count = chat.unreadMessages,
                image = chat.image,
            )
        }
    }
}

@Composable
private fun Chat(
    image: String?,
    title: String,
    actionType: RoomUi.LastActionUi.ActionTypeUi,
    time: String,
    author: String,
    count: Int,
    description: String?,
    modifier: Modifier = Modifier,
) {
    val actionRes = remember(actionType) {
        getChatActionDescription(actionType)
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DefaultAvatar(url = image)
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(weight = 1f),
                    overflow = TextOverflow.Ellipsis,
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    modifier = Modifier,
                    text = time,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = author,
                    style = MaterialTheme.typography.bodyMedium.bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = description ?: stringResource(actionRes),
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                if (count > 0) {
                    Text(
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.inversePrimary,
                                RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 4.dp),
                        text = "$count",
                        style = MaterialTheme.typography.labelSmall.semiBold
                    )
                }
            }
        }
    }
}


@Composable
private fun Error(
    error: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Some error occurred", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = error)

        Spacer(modifier = Modifier.height(8.dp))

        DefaultButton(onClick = onRetry, text = "Retry")
    }
}

@Composable
private fun Loading(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Loading", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(4.dp))

        CircularProgressIndicator(
            modifier = Modifier.size(64.dp),
            color = MaterialTheme.colorScheme.background,
            strokeCap = StrokeCap.Round
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorPreview() {
    MessengerTheme {
        Error(
            modifier = Modifier.fillMaxSize(),
            onRetry = {},
            error = "loading error"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingPreview() {
    MessengerTheme {
        Loading(
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SuccessPreview() {
    MessengerTheme {
        val rooms = listOf(
            RoomUi(
                id = 8307,
                name = null,
                image = null,
                lastAction = RoomUi.LastActionUi(
                    authorName = "Chelsea Stark",
                    actionType = RoomUi.LastActionUi.ActionTypeUi.USER_CREATE_ROOM,
                    description = null,
                    actionDateTime = "21:21"
                ),
                unreadMessages = 0
            ),
            RoomUi(
                id = 8307,
                name = null,
                image = null,
                lastAction = RoomUi.LastActionUi(
                    authorName = "Minor Update",
                    actionType = RoomUi.LastActionUi.ActionTypeUi.USER_SENT_MESSAGE,
                    description = "Hello",
                    actionDateTime = "21:22"
                ),
                unreadMessages = 0
            )
        )
        Success(
            chats = rooms,
            modifier = Modifier.fillMaxSize()
        )
    }
}