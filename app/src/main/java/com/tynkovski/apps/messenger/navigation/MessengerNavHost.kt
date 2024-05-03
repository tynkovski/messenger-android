package com.tynkovski.apps.messenger.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.tynkovski.apps.messenger.feature.chats.navigation.CHATS_ROUTE
import com.tynkovski.apps.messenger.feature.chats.navigation.chatsScreen
import com.tynkovski.apps.messenger.feature.contacts.navigation.contactsScreen
import com.tynkovski.apps.messenger.feature.settings.navigation.settingsScreen
import com.tynkovski.apps.messenger.ui.MessengerAppState

@Composable
fun MessengerNavHost(
    appState: MessengerAppState,
    onShowSnackbar: suspend (message: String, action: String?) -> Boolean,
    modifier: Modifier = Modifier,
    startDestination: String = CHATS_ROUTE,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        contactsScreen()
        chatsScreen()
        settingsScreen()
   }
}