package com.tynkovski.apps.messenger.feature.user.navigation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tynkovski.apps.messenger.feature.user.UserRoute

private val URL_CHARACTER_ENCODING = Charsets.UTF_8.name()

@VisibleForTesting
internal const val USER_ID_ARG = "room_id"

const val USER_ROUTE = "user_route"

internal class UserArgs(
    val userId: Long
) {
    constructor(
        savedStateHandle: SavedStateHandle
    ) : this(
        checkNotNull(savedStateHandle.get<Long>(USER_ID_ARG))
    )
}

fun NavController.navigateToUser(
    userId: Long,
    navOptions: NavOptionsBuilder.() -> Unit = {}
) {
    val newRoute = "$USER_ROUTE/$userId"
    navigate(newRoute) {
        navOptions()
    }
}

fun NavGraphBuilder.userScreen(
    onBackClick: () -> Unit,
) {
    composable(
        route = "$USER_ROUTE/{$USER_ID_ARG}",
        arguments = listOf(navArgument(USER_ID_ARG) { type = NavType.LongType })
    ) {
        UserRoute(
            onBackClick = onBackClick
        )
    }
}

