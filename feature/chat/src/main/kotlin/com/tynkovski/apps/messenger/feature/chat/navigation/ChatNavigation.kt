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
internal const val CHAT_ID_ARG = "room_id"
const val CHAT_ROUTE = "chat_route"

internal class ChatArgs(
    val chatId: String
) {
    constructor(
        savedStateHandle: SavedStateHandle
    ) : this(
        URLDecoder.decode(checkNotNull(savedStateHandle[CHAT_ID_ARG]), URL_CHARACTER_ENCODING)
    )
}

fun NavController.navigateToChat(
    chatId: String,
    navOptions: NavOptionsBuilder.() -> Unit = {}
) {
    val encodedId = URLEncoder.encode(chatId, URL_CHARACTER_ENCODING)
    val newRoute = "$CHAT_ROUTE/$encodedId"
    navigate(newRoute) {
        navOptions()
    }
}

fun NavGraphBuilder.chatScreen(
    onBackClick: () -> Unit,
) {
    composable(
        route = "$CHAT_ROUTE/{$CHAT_ID_ARG}",
        arguments = listOf(navArgument(CHAT_ID_ARG) { type = NavType.StringType })
    ) {
        ChatRoute(
            onBackClick = onBackClick
        )
    }
}
