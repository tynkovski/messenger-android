package com.tynkovski.apps.messenger.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.tracing.trace
import com.tynkovski.apps.messenger.feature.chats.navigation.CHATS_ROUTE
import com.tynkovski.apps.messenger.feature.chats.navigation.navigateToChats
import com.tynkovski.apps.messenger.feature.contacts.navigation.CONTACTS_ROUTE
import com.tynkovski.apps.messenger.feature.contacts.navigation.navigateToContacts
import com.tynkovski.apps.messenger.feature.settings.navigation.SETTINGS_ROUTE
import com.tynkovski.apps.messenger.feature.settings.navigation.navigateToSettings
import com.tynkovski.apps.messenger.navigation.TopLevelDestination

@Composable
fun rememberMessengerAppState(
    navController: NavHostController = rememberNavController(),
): MessengerAppState {
    return remember(navController) {
        MessengerAppState(navController = navController)
    }
}

@Stable
class MessengerAppState(
    val navController: NavHostController,
) {


    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            CONTACTS_ROUTE -> TopLevelDestination.CONTACTS
            CHATS_ROUTE -> TopLevelDestination.CHATS
            SETTINGS_ROUTE -> TopLevelDestination.SETTINGS
            else -> null
        }

    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
        trace("Navigation: ${topLevelDestination.name}") {
            when (topLevelDestination) {
                TopLevelDestination.CONTACTS -> navController.navigateToContacts(topLevelNavOptions)
                TopLevelDestination.CHATS -> navController.navigateToChats(topLevelNavOptions)
                TopLevelDestination.SETTINGS -> navController.navigateToSettings(topLevelNavOptions)
            }
        }
    }
}
