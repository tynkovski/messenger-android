package com.tynkovski.apps.messenger.feature.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.tynkovski.apps.messenger.core.designsystem.theme.MessengerTheme
import com.tynkovski.apps.messenger.core.ui.chat.ChatAppBar
import com.tynkovski.apps.messenger.core.ui.chat.ChatState
import com.tynkovski.apps.messenger.core.ui.error.Error
import com.tynkovski.apps.messenger.core.ui.loading.Loading
import com.tynkovski.apps.messenger.core.ui.message.MessageBubble

@Composable
internal fun ChatRoute(
    navigatePopBack: () -> Unit,
    navigateToUser: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = hiltViewModel(),
) {
    val paging = viewModel.pager.collectAsLazyPagingItems()
    val chatState by viewModel.roomState.collectAsStateWithLifecycle()

    ChatScreen(
        chatState = chatState,
        paging = paging,
        modifier = modifier,
        navigatePopBack = navigatePopBack,
        navigateToUser = navigateToUser,
    )
}

@Composable
internal fun ChatScreen(
    chatState: ChatState,
    paging: LazyPagingItems<MessageUi>,
    navigatePopBack: () -> Unit,
    navigateToUser: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
        topBar = {
            ChatAppBar(
                chatState = chatState,
                onPopBack = navigatePopBack,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    ) { padding ->
        PagingSuccess(
            modifier = modifier.padding(padding),
            paging = paging,
        )
    }
}

@Composable
private fun PagingSuccess(
    paging: LazyPagingItems<MessageUi>,
    modifier: Modifier,
) {
    LazyColumn(
        modifier = modifier,
        reverseLayout = true,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            count = paging.itemCount,
            key = paging.itemKey { it.id },
        ) {
            val message = paging[it] ?: return@items

            MessageBubble(
                owner = message.owner,
                text = message.text,
                date = message.sentAt,
                showDate = true,
                showTail = true,
                edited = message.editedAt != null,
                readIndicator = false,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            )
        }

        when {
            paging.loadState.source.refresh is LoadState.Error -> {
                item {
                    Error(
                        error = "Paging error",
                        onRetry = { },
                        modifier = modifier
                    )
                }

            }

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
private fun Message(
    modifier: Modifier,
    isSender: Boolean,
    read: Boolean,
    senderName: String?,
    sentAt: String,
    text: String,
) {
    Box(
        modifier = modifier,
        contentAlignment = if (isSender) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(modifier = Modifier, text = text)
            Text(modifier = Modifier, text = sentAt)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MessagePreview() {
    MessengerTheme {
        Message(
            modifier = Modifier.fillMaxWidth(),
            isSender = false,
            read = false,
            senderName = null,
            sentAt = "facilisis",
            text = "mentitum"
        )
    }
}