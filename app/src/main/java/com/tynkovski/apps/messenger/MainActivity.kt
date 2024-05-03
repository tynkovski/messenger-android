package com.tynkovski.apps.messenger

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tynkovski.apps.messenger.core.data.util.NetworkMonitor
import com.tynkovski.apps.messenger.core.data.util.TimeZoneMonitor
import com.tynkovski.apps.messenger.core.designsystem.theme.MessengerTheme
import com.tynkovski.apps.messenger.core.ui.LocalTimeZone
import com.tynkovski.apps.messenger.ui.MessengerApp
import com.tynkovski.apps.messenger.ui.rememberMessengerAppState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private val lightScrim = Color.argb(0xe6, 0xFF, 0xFF, 0xFF)
private val darkScrim = Color.argb(0x80, 0x1b, 0x1b, 0x1b)

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var networkMonitor: NetworkMonitor

    @Inject
    lateinit var timeZoneMonitor: TimeZoneMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        // todo should add splashScreen.setKeepOnScreenCondition {
        //      when (authViewModel.getAuthStatus().state().isLoading()) {
        //          AuthStatusState.IsLoading -> true
        //          AuthStatusState.Success -> false
        //      }
        // }

        setContent {
            val isSystemInDarkTheme = isSystemInDarkTheme()
            DisposableEffect(false) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        Color.TRANSPARENT,
                        Color.TRANSPARENT,
                    ) { isSystemInDarkTheme },
                    navigationBarStyle = SystemBarStyle.auto(
                        lightScrim,
                        darkScrim,
                    ) { isSystemInDarkTheme },
                )
                onDispose {}
            }

            val appState = rememberMessengerAppState(
                windowSizeClass = calculateWindowSizeClass(this),
                networkMonitor = networkMonitor,
                timeZoneMonitor = timeZoneMonitor,
            )

            val currentTimeZone by appState.currentTimeZone.collectAsStateWithLifecycle()

            CompositionLocalProvider(
                LocalTimeZone provides currentTimeZone,
            ) {
                MessengerTheme {
                    MessengerApp(appState)
                }
            }
        }
    }
}