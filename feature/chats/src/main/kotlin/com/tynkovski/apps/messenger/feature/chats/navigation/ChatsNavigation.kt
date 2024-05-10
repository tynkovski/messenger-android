package com.tynkovski.apps.messenger.feature.chats.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.tynkovski.apps.messenger.feature.chats.ChatsRoute

const val CHATS_ROUTE = "chats_route"

fun NavController.navigateToChats(navOptions: NavOptions) = navigate(CHATS_ROUTE, navOptions)

fun NavGraphBuilder.chatsScreen(
    navigateToChat: (Long) -> Unit,
) {
    composable(
        route = CHATS_ROUTE,
        arguments = listOf(),
    ) {
        ChatsRoute(
            navigateToChat = navigateToChat,
            modifier = Modifier.fillMaxSize()
        )
    }
}
