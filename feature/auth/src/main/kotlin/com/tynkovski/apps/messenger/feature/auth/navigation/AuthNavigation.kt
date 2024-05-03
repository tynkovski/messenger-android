package com.tynkovski.apps.messenger.feature.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.tynkovski.apps.messenger.feature.auth.AuthRoute

const val AUTH_ROUTE = "auth_route"

fun NavController.navigateToAuth() = navigate(AUTH_ROUTE) {
    popUpTo(graph.findStartDestination().id) {
        saveState = true
    }
    launchSingleTop = true
    restoreState = true
}

fun NavGraphBuilder.authScreen(

) {
    composable(
        route = AUTH_ROUTE,
        arguments = listOf(),
    ) {
        AuthRoute()
    }
}
