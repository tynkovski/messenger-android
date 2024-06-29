package com.tynkovski.apps.messenger.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.tynkovski.apps.messenger.core.designsystem.component.MessengerNavigationBar
import com.tynkovski.apps.messenger.core.designsystem.component.MessengerNavigationBarItem
import com.tynkovski.apps.messenger.core.designsystem.component.MessengerTopAppBar
import com.tynkovski.apps.messenger.core.designsystem.component.TransparentIconButton
import com.tynkovski.apps.messenger.core.designsystem.icon.MessengerIcons
import com.tynkovski.apps.messenger.navigation.MainNavHost
import com.tynkovski.apps.messenger.navigation.TopLevelDestination

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainScreen(
    appState: MessengerMainState,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    val destination = appState.currentTopLevelDestination
    val shouldShowTopAppBar = destination != null

    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = if (shouldShowTopAppBar) WindowInsets(0, 0, 0, 0) else WindowInsets.statusBars,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            when (destination) {
                TopLevelDestination.CONTACTS -> {
                    MessengerTopAppBar(
                        titleRes = destination.titleTextId,
                        actions = {
                            TransparentIconButton(
                                imageVector = MessengerIcons.Search,
                                onClick = appState::navigateToSearch
                            )
                        }
                    )
                }

                TopLevelDestination.SETTINGS -> {
                    MessengerTopAppBar(
                        titleRes = destination.titleTextId
                    )
                }

                TopLevelDestination.CHATS -> { Unit }

                null -> Unit
            }
        },
        bottomBar = {
            AnimatedVisibility(
                visible = destination != null,
                enter = expandVertically { -it },
                exit = shrinkVertically { -it }
            ) {
                MessengerBottomBar(
                    destinations = appState.topLevelDestinations,
                    currentDestination = appState.currentDestination,
                    onNavigateToDestination = appState::navigateToTopLevelDestination,
                    modifier = Modifier
                )
            }
        }
    ) { padding ->
        MainNavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            appState = appState,
            onShowSnackbar = { message, action ->
                snackbarHostState.showSnackbar(
                    message = message,
                    actionLabel = action,
                    duration = SnackbarDuration.Short,
                ) == SnackbarResult.ActionPerformed
            }
        )
    }
}

@Composable
private fun MessengerBottomBar(
    destinations: List<TopLevelDestination>,
    currentDestination: NavDestination?,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    MessengerNavigationBar(
        modifier = modifier
    ) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            MessengerNavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = destination.unselectedIcon,
                        contentDescription = null,
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = destination.selectedIcon,
                        contentDescription = null,
                    )
                },
                label = { Text(stringResource(destination.iconTextId)) },
                modifier = Modifier,
            )
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false

// for unread messages in chats
private fun Modifier.notificationDot(): Modifier = composed {
    val tertiaryColor = MaterialTheme.colorScheme.tertiary
    drawWithContent {
        drawContent()
        drawCircle(
            color = tertiaryColor,
            radius = 5.dp.toPx(),
            center = center + Offset(
                64.dp.toPx() * .45f,
                32.dp.toPx() * -.45f - 6.dp.toPx(),
            )
        )
    }
}