package com.tynkovski.apps.messenger.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tynkovski.apps.messenger.core.data.util.NetworkMonitor
import com.tynkovski.apps.messenger.core.data.util.TimeZoneMonitor
import com.tynkovski.apps.messenger.core.designsystem.component.MessengerBackground
import com.tynkovski.apps.messenger.core.ui.LocalTimeZone
import kotlinx.coroutines.flow.map
import kotlinx.datetime.TimeZone

@Composable
fun MessengerApp(
    authenticated: Boolean,
    timeZoneMonitor: TimeZoneMonitor,
    networkMonitor: NetworkMonitor,
    modifier: Modifier = Modifier,
) {
    MessengerBackground(modifier = Modifier.fillMaxSize()) {
        val currentTimeZone by timeZoneMonitor.currentTimeZone.collectAsStateWithLifecycle(
            initialValue = TimeZone.currentSystemDefault()
        )

        val isOffline by networkMonitor.isOnline
            .map(Boolean::not)
            .collectAsStateWithLifecycle(initialValue = true)

        val snackbarHostState = remember { SnackbarHostState() }

        LaunchedEffect(isOffline) {
            if (isOffline) {
                snackbarHostState.showSnackbar(
                    message = "You aren’t connected to the internet", // todo add to res
                    duration = SnackbarDuration.Indefinite,
                )
            }
        }

        CompositionLocalProvider(LocalTimeZone provides currentTimeZone) {
            AnimatedContent(
                targetState = authenticated,
                modifier = modifier,
                transitionSpec = {
                    val tweenSpec = tween<Float>(200)
                    val start = fadeIn(animationSpec = tweenSpec)
                    val end = fadeOut(animationSpec = tweenSpec)
                    start.togetherWith(end).using(SizeTransform(clip = false))
                },
                label = "AnimatedContent Main Auth Screen",
            ) {
                if (it) {
                    val appState = rememberMessengerMainState()
                    MainScreen(
                        appState = appState,
                        snackbarHostState = snackbarHostState,
                        modifier = Modifier.fillMaxSize(),
                    )
                } else {
                    val authState = rememberMessengerAuthState()
                    AuthScreen(
                        authState = authState,
                        snackbarHostState = snackbarHostState,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}