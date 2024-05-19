package com.tynkovski.apps.messenger.feature.chats

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.tynkovski.apps.messenger.core.designsystem.animation.AnimatedFlip
import com.tynkovski.apps.messenger.core.designsystem.component.DefaultAvatar
import com.tynkovski.apps.messenger.core.designsystem.component.TransparentIconButton
import com.tynkovski.apps.messenger.core.designsystem.icon.MessengerIcons
import com.tynkovski.apps.messenger.core.designsystem.theme.MessengerTheme
import com.tynkovski.apps.messenger.core.ui.bold
import com.tynkovski.apps.messenger.core.ui.collectAsSideEffect
import com.tynkovski.apps.messenger.core.ui.contact.Contact
import com.tynkovski.apps.messenger.core.ui.contact.ContactsUiState
import com.tynkovski.apps.messenger.core.ui.semiBold

@Composable
internal fun ChatsRoute(
    navigateToChat: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ChatsViewModel = hiltViewModel(),
) {
    val paging = viewModel.pager.collectAsLazyPagingItems()
    val contactsState by viewModel.contactsState.collectAsStateWithLifecycle()
    val connected by viewModel.isConnected.collectAsStateWithLifecycle()
    val selectedChats by viewModel.selectedChats.collectAsStateWithLifecycle()

    viewModel.sideEffect.collectAsSideEffect {
        when (it) {
            is ChatsSideEffect.NavigateToChat -> navigateToChat(it.chatId)
        }
    }

    ChatsScreen(
        connected = connected,
        selectedChats = selectedChats,
        contactsState = contactsState,
        paging = paging,
        modifier = modifier,
        onChatClick = navigateToChat,
        onChatLongClick = viewModel::selectChat,
        onContactClick = viewModel::findChatWithUser,
        deleteSelectedChats = viewModel::deleteSelectedChats,
        clearSelectedChats = viewModel::clearSelectedChats,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ChatsScreen(
    selectedChats: Set<Long>,
    deleteSelectedChats: () -> Unit,
    clearSelectedChats: () -> Unit,
    contactsState: ContactsUiState,
    paging: LazyPagingItems<RoomUi>,
    onChatClick: (Long) -> Unit,
    onChatLongClick: (Long) -> Unit,
    onContactClick: (Long) -> Unit,
    connected: Boolean,
    modifier: Modifier = Modifier,
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val selectedCount = remember(selectedChats) { selectedChats.size }
    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showBottomSheet = true },
            ) {
                Icon(
                    imageVector = MessengerIcons.CreateChat,
                    contentDescription = "Create Chat"
                )
            }
        },
        topBar = {
            if (selectedCount > 0) {
                CenterAlignedTopAppBar(
                    windowInsets = WindowInsets.statusBars,
                    title = {
                        Text(text = "Selected $selectedCount")
                    },
                    navigationIcon = {
                        TransparentIconButton(
                            imageVector = MessengerIcons.Close,
                            onClick = clearSelectedChats
                        )
                    },
                    actions = {
                        TransparentIconButton(
                            imageVector = MessengerIcons.Delete,
                            onClick = deleteSelectedChats
                        )
                    }
                )
            } else {
                CenterAlignedTopAppBar(
                    windowInsets = WindowInsets.statusBars,
                    title = {
                        Text(text = "Chats")
                    },
                )
            }
        }
    ) { padding ->
        Column(modifier = modifier.padding(padding)) {
            AnimatedVisibility(!connected) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                        .background(MaterialTheme.colorScheme.errorContainer),
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        style = MaterialTheme.typography.labelSmall,
                        text = "Not connected to websockets",
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }

            PagingSuccess(
                modifier = Modifier.weight(1f),
                paging = paging,
                selectedChats = selectedChats,
                onChatClick = onChatClick,
                onChatLongClick = onChatLongClick,
            )
        }

        if (showBottomSheet && contactsState is ContactsUiState.Success) {
            val contacts = contactsState.contacts
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState,
                windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding(),
                ) {
                    items(contacts.size) {
                        Contact(
                            contact = contacts[it],
                            onClick = { onContactClick(contacts[it].id) },
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PagingSuccess(
    paging: LazyPagingItems<RoomUi>,
    selectedChats: Set<Long>,
    onChatClick: (Long) -> Unit,
    onChatLongClick: (Long) -> Unit,
    modifier: Modifier,
) {
    val selectedCount = remember(selectedChats) { selectedChats.size }

    LazyColumn(modifier = modifier) {
        items(
            count = paging.itemCount,
            key = paging.itemKey { it.id },
        ) {
            val chat = paging[it] ?: return@items
            Chat(
                modifier = Modifier
                    .fillMaxWidth()
                    .combinedClickable(
                        onClick = {
                            if (selectedCount > 0) {
                                onChatLongClick(chat.id)
                            } else {
                                onChatClick(chat.id)
                            }
                        },
                        onLongClick = { onChatLongClick(chat.id) }
                    )
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                title = chat.name ?: chat.users.joinToString(", "),
                time = chat.lastAction.actionDateTime,
                actionType = chat.lastAction.actionType,
                author = chat.lastAction.authorName,
                description = chat.lastAction.description,
                count = chat.unreadMessages,
                image = chat.image,
                selected = chat.id in selectedChats
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
    selected: Boolean = false,
) {
    val actionRes = remember(actionType) {
        getChatActionDescription(actionType)
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AnimatedFlip(
            flip = selected,
            face = {
                DefaultAvatar(modifier = it, url = image)
            },
            back = {
                SelectedRoomIndicator(modifier = it)
            }
        )

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

@Composable
fun SelectedRoomIndicator(
    modifier: Modifier,
    shape: Shape = CircleShape,
    selectTint: Color = MaterialTheme.colorScheme.primary,
    arrowTint: Color = MaterialTheme.colorScheme.background,
) = Box(
    modifier = modifier
        .size(48.dp)
        .clip(shape)
        .background(SolidColor(selectTint), shape)
        .border(2.dp, SolidColor(selectTint), shape),
    contentAlignment = Alignment.Center
) {
    Icon(
        modifier = Modifier.size(24.dp),
        tint = arrowTint,
        imageVector = MessengerIcons.Done,
        contentDescription = null,
    )
}

@Preview(showBackground = true)
@Composable
private fun ChatsPreview() {
    MessengerTheme {
        Chat(
            image = null,
            title = "Some chat",
            actionType = RoomUi.LastActionUi.ActionTypeUi.USER_SENT_MESSAGE,
            time = "12:14",
            author = "Some Author",
            count = 0,
            description = "Some description",
            modifier = Modifier.fillMaxWidth()
        )
    }
}