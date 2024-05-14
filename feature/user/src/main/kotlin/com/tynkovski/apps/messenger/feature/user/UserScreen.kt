package com.tynkovski.apps.messenger.feature.user

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tynkovski.apps.messenger.core.model.Result
import com.tynkovski.apps.messenger.core.ui.collectAsSideEffect
import com.tynkovski.apps.messenger.core.ui.error.Error
import com.tynkovski.apps.messenger.core.ui.loading.Loading
import com.tynkovski.apps.messenger.core.ui.user.SuccessMyself
import com.tynkovski.apps.messenger.core.ui.user.SuccessSomebody
import com.tynkovski.apps.messenger.core.ui.user.UserUi

@Composable
internal fun UserRoute(
    navigatePopBack: () -> Unit,
    navigateToChat: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UserViewModel = hiltViewModel(),
) {
    val userState by viewModel.userState.collectAsStateWithLifecycle()

    viewModel.sideEffect.collectAsSideEffect {
        when (it) {
            is UserSideEffect.NavigateToRoom -> navigateToChat(it.roomId)
        }
    }

    UserScreen(
        state = userState,
        onRetry = viewModel::getContacts,
        addToContacts = viewModel::addToContacts,
        removeFromContacts = viewModel::removeFromContacts,
        toChatWithUser = viewModel::findChat,
        modifier = modifier,
    )
}

@Composable
internal fun UserScreen(
    state: Result<UserUi>,
    onRetry: () -> Unit,
    addToContacts: (Long) -> Unit,
    removeFromContacts: (Long) -> Unit,
    toChatWithUser: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (state) {
        is Result.Error -> {
            Error(
                error = state.exception.message.toString(),
                onRetry = onRetry,
                modifier = modifier
            )
        }

        Result.Loading -> {
            Loading(modifier = modifier)
        }

        is Result.Success -> {
            Success(
                user = state.data,
                addToContacts = addToContacts,
                removeFromContacts = removeFromContacts,
                toChatWithUser = toChatWithUser,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun Success(
    user: UserUi,
    addToContacts: (Long) -> Unit,
    toChatWithUser: (Long) -> Unit,
    removeFromContacts: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (user) {
        is UserUi.Myself -> {
            SuccessMyself(
                user = user,
                modifier = modifier.padding(vertical = 12.dp, horizontal = 16.dp)
            )
        }

        is UserUi.Somebody -> {
            SuccessSomebody(
                user = user,
                addToContacts = { addToContacts(user.id) },
                removeFromContacts = { removeFromContacts(user.id) },
                modifier = modifier.padding(vertical = 12.dp, horizontal = 16.dp),
                toChatWithUser = { toChatWithUser(user.id) },
            )
        }
    }
}
