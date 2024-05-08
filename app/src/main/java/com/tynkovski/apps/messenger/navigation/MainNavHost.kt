package com.tynkovski.apps.messenger.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.tynkovski.apps.messenger.feature.chat.navigation.navigateToChat
import com.tynkovski.apps.messenger.feature.chats.navigation.CHATS_ROUTE
import com.tynkovski.apps.messenger.feature.chats.navigation.chatsScreen
import com.tynkovski.apps.messenger.feature.contacts.navigation.contactsScreen
import com.tynkovski.apps.messenger.feature.search.navigation.searchScreen
import com.tynkovski.apps.messenger.feature.settings.navigation.settingsScreen
import com.tynkovski.apps.messenger.feature.user.navigation.navigateToUser
import com.tynkovski.apps.messenger.ui.MessengerMainState

@Composable
fun MainNavHost(
    appState: MessengerMainState,
    onShowSnackbar: suspend (message: String, action: String?) -> Boolean,
    modifier: Modifier = Modifier,
    startDestination: String = CHATS_ROUTE,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        enterTransition = { fadeIn(animationSpec = tween(200)) },
        exitTransition = { fadeOut(animationSpec = tween(200)) },
        popEnterTransition = { fadeIn(animationSpec = tween(200)) },
        popExitTransition = { fadeOut(animationSpec = tween(200)) },

        ) {
        contactsScreen()
        settingsScreen()
        chatsScreen(
            navigateToChat = navController::navigateToChat,
        )
        searchScreen(
            navigatePopBack = navController::popBackStack,
            navigateToUser = navController::navigateToUser,
            navigateToChat = navController::navigateToChat,
        )
    }
}