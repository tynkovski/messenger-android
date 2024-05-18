package com.tynkovski.apps.messenger.core.ui.message

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tynkovski.apps.messenger.core.designsystem.ext.isLtr
import com.tynkovski.apps.messenger.core.designsystem.helpers.chose
import com.tynkovski.apps.messenger.core.designsystem.icon.MessengerIcons
import com.tynkovski.apps.messenger.core.designsystem.shape.MessageBubbleShape
import com.tynkovski.apps.messenger.core.designsystem.shape.TailPosition
import com.tynkovski.apps.messenger.core.designsystem.theme.MessengerTheme

@Composable
@NonRestartableComposable
fun MessageBubble(
    text: String,
    date: String,
    showDate: Boolean,
    showTail: Boolean,
    edited: Boolean,
    owner: Boolean,
    readIndicator: Boolean,
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(8.dp),
    bias: Float = 0.78f,
    corners: Dp = 12.dp,
    tailWidth: Dp = 6.dp,
    arrowHeight: Dp = 16.dp,
    messageShape: Shape = MessageBubbleShape(
        corners = corners,
        arrowWidth = tailWidth,
        arrowHeight = arrowHeight,
        tailPosition = if (isLtr) {
            if (owner) TailPosition.Right else TailPosition.Left
        } else {
            if (owner) TailPosition.Left else TailPosition.Right
        },
        showTail = showTail,
    ),
    ownerMessageStyle: MessageStyle = messageDefaults(
        bubbleColor = MaterialTheme.colorScheme.inversePrimary,
        indicatorColor = MaterialTheme.colorScheme.inversePrimary,
        textColor = MaterialTheme.colorScheme.onPrimaryContainer,
        dateColor = MaterialTheme.colorScheme.onSecondaryContainer
    ),
    collocutorMessageStyle: MessageStyle = messageDefaults(
        bubbleColor = MaterialTheme.colorScheme.primaryContainer,
        indicatorColor = MaterialTheme.colorScheme.primaryContainer,
        textColor = MaterialTheme.colorScheme.onPrimaryContainer,
        dateColor = MaterialTheme.colorScheme.onSecondaryContainer
    ),
) = MessageBubbleInner(
    modifier = modifier,
    owner = owner,
    text = text,
    date = date,
    readIndicator = readIndicator,
    showDate = showDate,
    edited = edited,
    messageStyle = if (owner) ownerMessageStyle else collocutorMessageStyle,
    padding = padding,
    bias = bias,
    shape = messageShape,
    tailWidth = tailWidth,
)

@Composable
private fun MessageBubbleInner(
    owner: Boolean,
    text: String,
    date: String,
    readIndicator: Boolean,
    showDate: Boolean,
    edited: Boolean,
    messageStyle: MessageStyle,
    padding: PaddingValues,
    shape: Shape,
    bias: Float,
    tailWidth: Dp,
    modifier: Modifier = Modifier,
) {
    val indicator: (@Composable () -> Unit)? = remember(readIndicator) {
        if (readIndicator) {
            movableContentOf {
                ReadIndicator(color = messageStyle.indicatorColor)
            }
        } else null
    }

    val editedComposable: (@Composable () -> Unit)? = remember(edited) {
        if (edited) {
            movableContentOf {
                Icon(
                    modifier = Modifier.size(10.dp),
                    imageVector = MessengerIcons.Edited,
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = null
                )
            }
        } else null
    }

    val showDateComposable: (@Composable () -> Unit)? = remember(showDate) {
        if (showDate) {
            movableContentOf {
                Text(
                    text = date,
                    color = messageStyle.dateColor,
                    style = messageStyle.dateStyle,
                    maxLines = 1
                )
            }
        } else null
    }

    MessageBubbleBase(
        modifier = modifier,
        owner = owner,
        readIndicator = indicator,
        bias = bias,
    ) {
        Surface(
            modifier = Modifier
                .weight(bias, fill = false)
                .widthIn(min = 40.dp),
            color = messageStyle.bubbleColor,
            shape = shape,
        ) {
            MessageBubbleContent(
                modifier = Modifier
                    .padding(padding)
                    .chose(
                        value = owner,
                        block = { padding(end = tailWidth) },
                        another = { padding(start = tailWidth) }
                    ),
                text = {
                    Text(
                        text = text,
                        color = messageStyle.textColor,
                        style = messageStyle.textStyle,
                    )
                },
                indicators = {
                    editedComposable?.invoke()
                    showDateComposable?.invoke()
                }
            )
        }
    }
}

@Preview
@Composable
private fun MessagesPreview2() {
    val previewShortMessage = "Hello"
    MessengerTheme {
        Column {
            MessageBubble(
                owner = true,
                text = previewShortMessage,
                date = "11:11",
                showDate = true,
                showTail = true,
                edited = true,
                readIndicator = false,
                modifier = Modifier.fillMaxWidth(),
            )

            MessageBubble(
                owner = false,
                text = previewShortMessage,
                date = "11:11",
                showDate = true,
                showTail = true,
                edited = false,
                readIndicator = true,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
