package com.tynkovski.apps.messenger.feature.chats

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.tynkovski.apps.messenger.core.designsystem.component.DefaultAvatar
import com.tynkovski.apps.messenger.core.ui.bold
import com.tynkovski.apps.messenger.core.ui.error.Error
import com.tynkovski.apps.messenger.core.ui.loading.Loading
import com.tynkovski.apps.messenger.core.ui.semiBold

@Composable
internal fun ChatsRoute(
    navigateToChat: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ChatsViewModel = hiltViewModel(),
) {
    val paging = viewModel.pager.collectAsLazyPagingItems()
    ChatsScreen(
        paging = paging,
        modifier = modifier,
        onChatClick = navigateToChat
    )
}

@Composable
internal fun ChatsScreen(
    paging: LazyPagingItems<RoomUi>,
    onChatClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    PagingSuccess(
        modifier = modifier,
        paging = paging,
        onChatClick = onChatClick
    )
    if (paging.loadState.source.refresh is LoadState.Error)  {
        Error(
            error = "Paging error",
            onRetry = { },
            modifier = modifier
        )
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
private fun PagingSuccess(
    paging: LazyPagingItems<RoomUi>,
    onChatClick: (Long) -> Unit,
    modifier: Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(
            count = paging.itemCount,
            key = paging.itemKey { it.id },
        ) {
            val chat = paging[it] ?: return@items
            Chat(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { onChatClick(chat.id) })
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

        when {
            paging.loadState.source.append is LoadState.Error -> {
                item {
                    Error("Load error", {})
                }
            }

            paging.loadState.source.append is LoadState.Loading -> {
                item {
                    Loading()
                }
            }
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
                    text = description ?: stringResource(actionRes),
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f),
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