package com.tynkovski.apps.messenger.core.ui.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tynkovski.apps.messenger.core.designsystem.component.DefaultAvatar
import com.tynkovski.apps.messenger.core.designsystem.component.DefaultIconButton
import com.tynkovski.apps.messenger.core.designsystem.icon.MessengerIcons
import com.tynkovski.apps.messenger.core.designsystem.theme.MessengerTheme

@Composable
fun SuccessSomebody(
    user: UserUi.Somebody,
    toChatWithUser: () -> Unit,
    addToContacts: () -> Unit,
    removeFromContacts: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DefaultAvatar(
                url = user.image,
                size = 128.dp
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DefaultIconButton(
                    imageVector = if (user.inContacts) MessengerIcons.RemoveFromContacts else MessengerIcons.AddToContacts,
                    onClick = if (user.inContacts) removeFromContacts else addToContacts
                )
                DefaultIconButton(
                    imageVector = MessengerIcons.Message,
                    onClick = toChatWithUser
                )
            }
        }
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            ListItem(title = "id", description = user.id.toString())
            ListItem(title = "login", description = user.login)
            user.name?.let { ListItem(title = "name", description = it) }
            ListItem(title = "created at", description = user.createdAt)
        }
    }
}

@Composable
fun SuccessMyself(
    user: UserUi.Myself,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DefaultAvatar(
                url = user.image,
                size = 128.dp
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            ListItem(title = "id", description = user.id.toString())
            ListItem(title = "login", description = user.login)
            user.name?.let { ListItem(title = "name", description = it) }
            ListItem(title = "created at", description = user.createdAt)
        }
    }
}

@Composable
private fun ListItem(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall.copy(
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            modifier = Modifier,
            text = description,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview1() {
    MessengerTheme {
        SuccessSomebody(
            user = UserUi.Somebody(
                id = 8289,
                login = "voluptat",
                name = "repudiare",
                image = null,
                createdAt = "21.12.2021",
                isDeleted = false,
                inContacts = false
            ),
            addToContacts = { },
            removeFromContacts = { },
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            toChatWithUser = { },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview2() {
    MessengerTheme {
        SuccessMyself(
            user = UserUi.Myself(
                id = 7372,
                login = "repudiare",
                name = null,
                image = null,
                createdAt = "21.12.2021",
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 12.dp, horizontal = 16.dp),
        )
    }
}