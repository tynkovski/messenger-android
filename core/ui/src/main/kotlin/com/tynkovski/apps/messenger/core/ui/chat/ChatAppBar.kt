package com.tynkovski.apps.messenger.core.ui.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.tynkovski.apps.messenger.core.designsystem.component.DefaultAvatar
import com.tynkovski.apps.messenger.core.designsystem.component.TransparentIconButton
import com.tynkovski.apps.messenger.core.designsystem.icon.MessengerIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatAppBar(
    chatState: ChatState,
    onPopBack: () -> Unit,
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
) {
    TopAppBar(
        modifier = modifier,
        windowInsets = windowInsets,
        title = {
            when (chatState) {
                is ChatState.Error -> {
                    SimpleError(error = chatState.exception.message.toString())
                }

                ChatState.Loading -> {
                    SimpleLoading()
                }

                is ChatState.Success -> {
                    val userNames = remember(chatState) {
                        chatState.users.joinToString(", ")
                    }
                    SimpleContent(
                        modifier = Modifier.fillMaxWidth(),
                        title = chatState.name ?: userNames,
                        description = null,
                        image = chatState.image,
                    )
                }
            }
        },
        navigationIcon = {
            TransparentIconButton(
                imageVector = MessengerIcons.ArrowBack,
                onClick = onPopBack,
                iconTint = MaterialTheme.colorScheme.onSurface,
                ignoreMinimumTouch = false,
            )
        }
    )
}

@Composable
fun SimpleLoading(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        DefaultAvatar(url = null)
        Text(modifier = Modifier.weight(1f), text = "Loading", maxLines = 1)
    }
}

@Composable
fun SimpleError(
    error: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        DefaultAvatar(url = null)
        Text(modifier = Modifier.weight(1f), text = error, maxLines = 1)
    }
}

@Composable
private fun SimpleContent(
    image: String?,
    title: String,
    description: String?,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        DefaultAvatar(url = image)
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            description?.let {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
