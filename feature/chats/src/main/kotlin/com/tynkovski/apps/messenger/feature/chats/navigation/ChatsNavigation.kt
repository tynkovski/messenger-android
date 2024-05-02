package com.tynkovski.apps.messenger.feature.chats.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.tynkovski.apps.messenger.feature.chats.ChatsRoute

const val _ROUTE = "_route"

fun NavController.navigateToChats(navOptions: NavOptions) = navigate(_ROUTE, navOptions)

fun NavGraphBuilder.ChatsScreen(

) {
    composable(
        route = _ROUTE,
        arguments = listOf(),
    ) {
        ChatsRoute()
    }
}
