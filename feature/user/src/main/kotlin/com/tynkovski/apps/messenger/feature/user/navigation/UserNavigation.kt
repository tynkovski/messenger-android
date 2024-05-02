package com.tynkovski.apps.messenger.feature.user.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.tynkovski.apps.messenger.feature.user.UserRoute

const val _ROUTE = "_route"

fun NavController.navigateToUser(navOptions: NavOptions) = navigate(_ROUTE, navOptions)

fun NavGraphBuilder.UserScreen(

) {
    composable(
        route = _ROUTE,
        arguments = listOf(),
    ) {
        UserRoute()
    }
}
