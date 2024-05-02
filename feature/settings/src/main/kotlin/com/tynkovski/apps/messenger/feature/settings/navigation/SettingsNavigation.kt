package com.tynkovski.apps.messenger.feature.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.tynkovski.apps.messenger.feature.settings.SettingsRoute

const val _ROUTE = "_route"

fun NavController.navigateToSettings(navOptions: NavOptions) = navigate(_ROUTE, navOptions)

fun NavGraphBuilder.SettingsScreen(

) {
    composable(
        route = _ROUTE,
        arguments = listOf(),
    ) {
        SettingsRoute()
    }
}
