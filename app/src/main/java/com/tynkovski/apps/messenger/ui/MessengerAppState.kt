package com.tynkovski.apps.messenger.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.tracing.trace
import com.tynkovski.apps.messenger.core.data.util.NetworkMonitor
import com.tynkovski.apps.messenger.core.data.util.TimeZoneMonitor
import com.tynkovski.apps.messenger.feature.auth.navigation.navigateToAuth
import com.tynkovski.apps.messenger.feature.chat.navigation.navigateToChat
import com.tynkovski.apps.messenger.feature.chats.navigation.CHATS_ROUTE
import com.tynkovski.apps.messenger.feature.chats.navigation.navigateToChats
import com.tynkovski.apps.messenger.feature.contacts.navigation.CONTACTS_ROUTE
import com.tynkovski.apps.messenger.feature.contacts.navigation.navigateToContacts
import com.tynkovski.apps.messenger.feature.settings.navigation.SETTINGS_ROUTE
import com.tynkovski.apps.messenger.feature.settings.navigation.navigateToSettings
import com.tynkovski.apps.messenger.navigation.TopLevelDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.TimeZone

@Composable
fun rememberMessengerAppState(
    windowSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor,
    timeZoneMonitor: TimeZoneMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): MessengerAppState {
    return remember(
        navController,
        coroutineScope,
        windowSizeClass,
        networkMonitor,
        timeZoneMonitor,
    ) {
        MessengerAppState(
            navController = navController,
            windowSizeClass = windowSizeClass,
            coroutineScope = coroutineScope,
            networkMonitor = networkMonitor,
            timeZoneMonitor = timeZoneMonitor,
        )
    }
}

@Stable
class MessengerAppState(
    val navController: NavHostController,
    val windowSizeClass: WindowSizeClass,
    coroutineScope: CoroutineScope,
    networkMonitor: NetworkMonitor,
    timeZoneMonitor: TimeZoneMonitor,
) {
    val shouldShowBottomBar = true // todo use `windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact`

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

    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )


    val currentTimeZone = timeZoneMonitor.currentTimeZone.stateIn(
        coroutineScope,
        SharingStarted.WhileSubscribed(5_000),
        TimeZone.currentSystemDefault(),
    )

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation: ${topLevelDestination.name}") {
            val topLevelNavOptions = navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }

            when (topLevelDestination) {
                TopLevelDestination.CONTACTS -> navController.navigateToContacts(topLevelNavOptions)
                TopLevelDestination.CHATS -> navController.navigateToChats(topLevelNavOptions)
                TopLevelDestination.SETTINGS -> navController.navigateToSettings(topLevelNavOptions)
            }
        }
    }

    fun navigateToAuth() = navController.navigateToAuth()
}
