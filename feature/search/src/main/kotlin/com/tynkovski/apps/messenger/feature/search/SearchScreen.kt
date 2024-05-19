package com.tynkovski.apps.messenger.feature.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tynkovski.apps.messenger.core.designsystem.component.DefaultIconButton
import com.tynkovski.apps.messenger.core.designsystem.component.MessengerTopAppBar
import com.tynkovski.apps.messenger.core.designsystem.component.ThemePreviews
import com.tynkovski.apps.messenger.core.designsystem.component.TransparentIconButton
import com.tynkovski.apps.messenger.core.designsystem.icon.MessengerIcons
import com.tynkovski.apps.messenger.core.designsystem.theme.MessengerTheme
import com.tynkovski.apps.messenger.core.model.data.User
import com.tynkovski.apps.messenger.core.ui.collectAsSideEffect
import com.tynkovski.apps.messenger.core.ui.error.Error
import com.tynkovski.apps.messenger.core.ui.loading.Loading
import java.time.LocalDateTime

@Composable
internal fun SearchRoute(
    navigatePopBack: () -> Unit,
    navigateToUser: (Long) -> Unit,
    navigateToChat: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val query by viewModel.queryState.collectAsState()
    val state by viewModel.searchState.collectAsState()

    viewModel.sideEffect.collectAsSideEffect {
        when (it) {
            is SearchSideEffect.NavigateToChat -> navigateToChat(it.chatId)
            is SearchSideEffect.NavigateToUser -> navigateToUser(it.userId)
        }
    }

    SearchScreen(
        state = state,
        query = query,
        queryChanged = viewModel::setQuery,
        modifier = modifier,
        navigateToUser = navigateToUser,
        navigatePopBack = navigatePopBack,
        createChatWithUser = viewModel::createRoom,
        addToContacts = viewModel::addToContacts,
        removeFromContacts = viewModel::removeFromContacts,
        getContacts = viewModel::getContacts
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchScreen(
    state: SearchUiState,
    query: String,
    queryChanged: (String) -> Unit,
    navigateToUser: (Long) -> Unit,
    createChatWithUser: (Long) -> Unit,
    addToContacts: (Long) -> Unit,
    removeFromContacts: (Long) -> Unit,
    navigatePopBack: () -> Unit,
    getContacts: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets.navigationBars
            .union(WindowInsets.statusBars)
            .union(WindowInsets.displayCutout)
            .union(WindowInsets.ime),
        topBar = {
            MessengerTopAppBar(
                titleRes = R.string.feature_search_title_search,
                actions = {
                    TransparentIconButton(
                        imageVector = MessengerIcons.ArrowBack,
                        onClick = navigatePopBack
                    )
                }
            )
        }
    ) { padding ->

        val hasText by remember(query) {
            derivedStateOf { query.isNotEmpty() }
        }

        val removeTextButton: (@Composable () -> Unit)? = if (hasText) {
            {
                IconButton(
                    onClick = { queryChanged("") }
                ) {
                    Icon(
                        imageVector = MessengerIcons.Close,
                        contentDescription = null
                    )
                }
            }
        } else null

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(vertical = 12.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = query,
                onValueChange = queryChanged,
                shape = RoundedCornerShape(20.dp),
                placeholder = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Search", // todo make text res
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.outline
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                    errorTextColor = MaterialTheme.colorScheme.error,
                    errorSupportingTextColor = MaterialTheme.colorScheme.error,
                    errorTrailingIconColor = MaterialTheme.colorScheme.error,
                    errorLeadingIconColor = MaterialTheme.colorScheme.error,
                ),
                trailingIcon = removeTextButton
            )

            Spacer(modifier = Modifier.height(8.dp))

            when (state) {
                SearchUiState.EmptyQuery -> {
                    EmptySearch(
                        modifier = Modifier.fillMaxSize()
                    )
                }

                is SearchUiState.LoadFailed -> {
                    Error(
                        error = state.exception.message.toString(),
                        onRetry = getContacts,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                SearchUiState.Loading -> {
                    Loading(modifier = Modifier.fillMaxSize())
                }

                is SearchUiState.Success -> {
                    Success(
                        user = state.user,
                        inContacts = state.inContacts,
                        navigateToUser = { navigateToUser(state.user.id) },
                        createChatWithUser = { createChatWithUser(state.user.id) },
                        addToContacts = { addToContacts(state.user.id) },
                        removeFromContacts = { removeFromContacts(state.user.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun Success(
    user: User,
    inContacts: Boolean,
    navigateToUser: () -> Unit,
    createChatWithUser: () -> Unit,
    addToContacts: () -> Unit,
    removeFromContacts: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            with(user) {
                UserNode("ID", id.toString())
                UserNode("login", login)
                name?.let { UserNode("name", it) }
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DefaultIconButton(
                imageVector = if (inContacts) MessengerIcons.RemoveFromContacts else MessengerIcons.AddToContacts,
                onClick = if (inContacts) removeFromContacts else addToContacts
            )
            DefaultIconButton(
                imageVector = MessengerIcons.NewChat,
                onClick = createChatWithUser
            )
            DefaultIconButton(
                imageVector = MessengerIcons.Profile,
                onClick = navigateToUser
            )
        }
    }
}

@Composable
private fun EmptySearch(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Search people", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "And add them to your contacts", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun UserNode(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.weight(0.2f),
            text = title,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            modifier = Modifier.weight(1f),
            text = description
        )
    }
}

@ThemePreviews
@Composable
private fun SuccessUserSearchPreview() {
    MessengerTheme {
        Success(
            user = User(
                id = 6251,
                login = "blandit",
                name = "null",
                image = null,
                createdAt = LocalDateTime.now(),
                isDeleted = false
            ),
            navigateToUser = {},
            createChatWithUser = {},
            addToContacts = {},
            inContacts = true,
            removeFromContacts = {}
        )
    }
}
