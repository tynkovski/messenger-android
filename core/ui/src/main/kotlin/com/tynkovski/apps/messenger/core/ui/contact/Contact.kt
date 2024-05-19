package com.tynkovski.apps.messenger.core.ui.contact

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tynkovski.apps.messenger.core.designsystem.component.DefaultAvatar
import com.tynkovski.apps.messenger.core.designsystem.theme.MessengerTheme

@Composable
fun Contact(
    contact: ContactUi,
    onClick: () -> Unit,
    modifier: Modifier,
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(16.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DefaultAvatar(url = contact.image)

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                overflow = TextOverflow.Ellipsis,
                text = contact.login,
                style = MaterialTheme.typography.titleSmall
            )
            contact.name?.let {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    modifier = Modifier,
                    text = it,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            // buttons
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ContactPreview() {
    MessengerTheme {
        Contact(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp),
            contact = ContactUi(
                id = 4553, login = "Some login", name = null, image = null
            ),
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ContactPreview2() {
    MessengerTheme {
        Contact(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp),
            contact = ContactUi(
                id = 4553, login = "Some login", name = "Some name", image = null
            ),
            onClick = {}
        )
    }
}