package com.tynkovski.apps.messenger.feature.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.tynkovski.apps.messenger.feature.settings.SettingsRoute

const val SETTINGS_ROUTE = "settings_route"

fun NavController.navigateToSettings(navOptions: NavOptions) = navigate(SETTINGS_ROUTE, navOptions)

fun NavGraphBuilder.settingsScreen(

) {
    composable(
        route = SETTINGS_ROUTE,
        arguments = listOf(),
    ) {
        SettingsRoute()
    }
}
