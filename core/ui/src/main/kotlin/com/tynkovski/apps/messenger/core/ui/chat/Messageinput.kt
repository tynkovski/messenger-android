package com.tynkovski.apps.messenger.core.ui.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tynkovski.apps.messenger.core.designsystem.component.CustomTextField
import com.tynkovski.apps.messenger.core.designsystem.component.TransparentIconButton
import com.tynkovski.apps.messenger.core.designsystem.icon.MessengerIcons
import com.tynkovski.apps.messenger.core.designsystem.theme.MessengerTheme

@Composable
fun MessageInput(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    maxLines: Int = 5,
    corners: Dp = 24.dp,
) = Row(
    modifier = modifier,
    verticalAlignment = Alignment.Bottom,
    horizontalArrangement = Arrangement.spacedBy(8.dp)
) {
    CustomTextField(
        modifier = Modifier
            .weight(1f)
            .wrapContentHeight(),
        value = value,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(corners),
        maxLines = maxLines,
        hint = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = hint
            )
        },
    )

    TransparentIconButton(
        modifier = Modifier.size(48.dp),
        imageVector = MessengerIcons.Send,
        onClick = onClick,
    )
}

@Preview(showBackground = true)
@Composable
private fun MessageInputPreview() {
    MessengerTheme {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            MessageInput(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = {},
                onClick = {},
                hint = "Type a message...",
                maxLines = 5,
            )
        }
    }
}