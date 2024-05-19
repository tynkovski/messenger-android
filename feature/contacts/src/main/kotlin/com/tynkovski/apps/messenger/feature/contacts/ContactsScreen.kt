@file:OptIn(ExperimentalMaterial3Api::class)

package com.tynkovski.apps.messenger.feature.contacts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tynkovski.apps.messenger.core.designsystem.component.DefaultAvatar
import com.tynkovski.apps.messenger.core.designsystem.theme.MessengerTheme
import com.tynkovski.apps.messenger.core.ui.contact.Contact
import com.tynkovski.apps.messenger.core.ui.contact.ContactUi
import com.tynkovski.apps.messenger.core.ui.contact.ContactsUiState
import com.tynkovski.apps.messenger.core.ui.error.Error
import com.tynkovski.apps.messenger.core.ui.loading.Loading

@Composable
internal fun ContactsRoute(
    onUserClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ContactsViewModel = hiltViewModel(),
) {
    val state by viewModel.contacts.collectAsStateWithLifecycle()
    ContactsScreen(
        state = state,
        onRetry = viewModel::getContacts,
        modifier = modifier,
        onUserClick = onUserClick
    )
}

@Composable
internal fun ContactsScreen(
    state: ContactsUiState,
    onRetry: () -> Unit,
    onUserClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val refreshState = rememberPullToRefreshState()

    if (refreshState.isRefreshing) {
        onRetry()
        refreshState.endRefresh()
    }

    Box(
        modifier = modifier.nestedScroll(refreshState.nestedScrollConnection),
    ) {
        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = refreshState,
        )

        when (state) {
            ContactsUiState.Empty -> {
                Empty(modifier = Modifier.fillMaxSize())
            }

            is ContactsUiState.Error -> {
                Error(
                    error = state.exception.message.toString(),
                    onRetry = onRetry,
                    modifier = Modifier.fillMaxSize()
                )
            }

            ContactsUiState.Loading -> {
                Loading(modifier = Modifier.fillMaxSize())
            }

            is ContactsUiState.Success -> {
                Success(
                    contacts = state.contacts,
                    modifier = Modifier.fillMaxSize(),
                    onContactClick = onUserClick
                )
            }
        }
    }
}

@Composable
private fun Success(
    contacts: List<ContactUi>,
    onContactClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(contacts.size, key = { contacts[it].id }) {
            Contact(
                contact = contacts[it],
                modifier = Modifier.fillMaxWidth(),
                onClick = { onContactClick(contacts[it].id) }
            )
        }
    }
}

@Composable
private fun Empty(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "You have no contacts", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "You can add somebody", style = MaterialTheme.typography.bodyLarge)

    }
}

