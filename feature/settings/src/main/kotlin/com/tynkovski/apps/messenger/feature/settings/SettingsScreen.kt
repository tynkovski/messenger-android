package com.tynkovski.apps.messenger.feature.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tynkovski.apps.messenger.core.designsystem.component.DefaultAvatar
import com.tynkovski.apps.messenger.core.designsystem.component.DefaultButton
import com.tynkovski.apps.messenger.core.designsystem.icon.MessengerIcons
import com.tynkovski.apps.messenger.core.designsystem.theme.Blue30
import com.tynkovski.apps.messenger.core.designsystem.theme.Green40
import com.tynkovski.apps.messenger.core.designsystem.theme.Orange40
import com.tynkovski.apps.messenger.core.designsystem.theme.Purple40
import com.tynkovski.apps.messenger.core.designsystem.theme.Red30
import com.tynkovski.apps.messenger.core.model.Result
import com.tynkovski.apps.messenger.core.model.data.User
import com.tynkovski.apps.messenger.core.ui.divider.LightDivider
import com.tynkovski.apps.messenger.core.ui.group.DefaultGroup
import com.tynkovski.apps.messenger.core.ui.settings.ImageSettingsItem
import com.tynkovski.apps.messenger.core.ui.settings.TitleSettingsItem

private val topPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 14.dp, bottom = 10.dp)
private val middlePadding = PaddingValues(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 10.dp)
private val bottomPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 14.dp)

@Composable
internal fun SettingsRoute(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val user by viewModel.userState.collectAsStateWithLifecycle()

    SettingsScreen(
        state = user,
        modifier = modifier,
        signOut = viewModel::signOut,
    )
}

@Composable
internal fun SettingsScreen(
    state: Result<User>,
    signOut: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 12.dp),
        ) {
            if (state is Result.Success) {
                DefaultAvatar(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    url = state.data.image,
                    size = 96.dp
                )
                Spacer(modifier = Modifier.height(4.dp))
                DefaultGroup(
                    modifier = Modifier.fillMaxWidth(),
                    title = "Account"
                ) {
                    val top = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 6.dp)
                    val bottom =
                        PaddingValues(start = 16.dp, end = 16.dp, top = 6.dp, bottom = 12.dp)

                    TitleSettingsItem(
                        text = state.data.login,
                        title = "Login",
                        maxLinesContent = 4,
                        padding = top
                    )

                    LightDivider()

                    TitleSettingsItem(
                        text = state.data.name ?: "",
                        title = "Username",
                        padding = bottom
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
            }

            DefaultGroup(
                modifier = Modifier.fillMaxWidth(),
                title = "Settings"
            ) {
                ImageSettingsItem(
                    text = "Chat Settings",
                    imageVector = MessengerIcons.Forum,
                    padding = topPadding,
                    tint = Blue30,
                    onClick = { }
                )

                LightDivider()

                ImageSettingsItem(
                    text = "Privacy and Security",
                    imageVector = MessengerIcons.Lock,
                    padding = middlePadding,
                    tint = Red30,
                    onClick = { }
                )

                LightDivider()

                ImageSettingsItem(
                    text = "Notifications",
                    imageVector = MessengerIcons.Notifications,
                    padding = bottomPadding,
                    tint = Green40,
                    onClick = {}
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            DefaultGroup(
                modifier = Modifier.fillMaxWidth(),
                title = "Help"
            ) {
                ImageSettingsItem(
                    text = "Privacy Policy",
                    imageVector = MessengerIcons.PrivacyPolicy,
                    padding = topPadding,
                    tint = Purple40,
                    onClick = { }
                )

                LightDivider()

                ImageSettingsItem(
                    text = "Messenger FAQ",
                    imageVector = MessengerIcons.Help,
                    padding = bottomPadding,
                    tint = Orange40
                )
            }


            Spacer(modifier = Modifier.height(4.dp))

            DefaultGroup(
                modifier = Modifier.fillMaxWidth(),
                title = "Entry"
            ) {
                DefaultButton(
                    leadingIcon = MessengerIcons.Logout,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = signOut, text = "Sign Out",
                    colors = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError,
                        disabledContainerColor = MaterialTheme.colorScheme.error,
                        disabledContentColor = MaterialTheme.colorScheme.onError,
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.labelMedium,
                text = "by tynkovski",
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.labelSmall,
                text = "messenger v0.11.29.1",
                textAlign = TextAlign.Center
            )

        }
    }
}