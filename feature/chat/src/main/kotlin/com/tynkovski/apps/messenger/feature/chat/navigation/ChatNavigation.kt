package com.tynkovski.apps.messenger.feature.chat.navigation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tynkovski.apps.messenger.feature.chat.ChatRoute
import java.net.URLDecoder
import java.net.URLEncoder

private val URL_CHARACTER_ENCODING = Charsets.UTF_8.name()

@VisibleForTesting
internal const val CHAT_ID_ARG = "chatId"
const val CHAT_ROUTE = "chat_route"

internal class ChatArgs(
    val chatId: Long
) {
    constructor(
        savedStateHandle: SavedStateHandle
    ) : this(
        checkNotNull(savedStateHandle.get<Long>(CHAT_ID_ARG))
    )
}

fun NavController.navigateToChat(
    chatId: Long,
    navOptions: NavOptionsBuilder.() -> Unit = {}
) {
    val newRoute = "$CHAT_ROUTE/$chatId"
    navigate(newRoute) {
        navOptions()
    }
}

fun NavGraphBuilder.chatScreen(
    onBackClick: () -> Unit,
) {
    composable(
        route = "$CHAT_ROUTE/{$CHAT_ID_ARG}",
        arguments = listOf(navArgument(CHAT_ID_ARG) { type = NavType.LongType })
    ) {
        ChatRoute(
            onBackClick = onBackClick
        )
    }
}
