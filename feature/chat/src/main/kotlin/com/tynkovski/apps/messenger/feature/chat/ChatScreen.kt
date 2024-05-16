package com.tynkovski.apps.messenger.feature.chat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.tynkovski.apps.messenger.core.ui.error.Error
import com.tynkovski.apps.messenger.core.ui.loading.Loading

@Composable
internal fun ChatRoute(
    navigatePopBack: () -> Unit,
    navigateToUser: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = hiltViewModel(),
) {
    val paging = viewModel.pager.collectAsLazyPagingItems()
    val myselfId = viewModel.myselfId
    val chatId = viewModel.chatArgs.chatId
    ChatScreen(
        myselfId = myselfId,
        paging = paging,
        modifier = modifier,
        navigatePopBack = navigatePopBack,
        navigateToUser = navigateToUser,
    )
}

@Composable
internal fun ChatScreen(
    myselfId: Long,
    paging: LazyPagingItems<MessageUi>,
    navigatePopBack: () -> Unit,
    navigateToUser: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
        topBar = {

        }
    ) { padding ->
        PagingSuccess(
            myselfId = myselfId,
            modifier = modifier.padding(padding),
            paging = paging,
        )
    }
}

@Composable
private fun PagingSuccess(
    myselfId: Long,
    paging: LazyPagingItems<MessageUi>,
    modifier: Modifier,
) {
    LazyColumn(
        modifier = modifier,
        reverseLayout = true
    ) {
        items(
            count = paging.itemCount,
            key = paging.itemKey { it.id },
        ) {
            val message = paging[it] ?: return@items
            Message(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                isSender = message.senderId == myselfId,
                senderName = message.senderName,
                sentAt = message.sentAt,
                read = message.read,
                text = message.text,
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
        Column {
            Text(modifier = Modifier, text = text)
            Text(modifier = Modifier, text = sentAt)
        }
    }
}