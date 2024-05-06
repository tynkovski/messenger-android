package com.tynkovski.apps.messenger.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.tynkovski.apps.messenger.feature.auth.navigation.AUTH_ROUTE_SIGN_IN
import com.tynkovski.apps.messenger.feature.auth.navigation.signInScreen
import com.tynkovski.apps.messenger.feature.auth.navigation.signUpScreen
import com.tynkovski.apps.messenger.ui.MessengerAuthState

@Composable
fun AuthNavHost(
    authState: MessengerAuthState,
    onShowSnackbar: suspend (message: String, action: String?) -> Boolean,
    modifier: Modifier = Modifier,
    startDestination: String = AUTH_ROUTE_SIGN_IN,
) {
    NavHost(
        navController = authState.navController,
        startDestination = startDestination,
        modifier = modifier,
        enterTransition = { slideInHorizontally { it } },
        exitTransition = { slideOutHorizontally { -it } },
        popEnterTransition = { slideInHorizontally { -it } },
        popExitTransition = { slideOutHorizontally { it } }
    ) {
        signInScreen(
            navigateToSignUp = authState::navigateToSignUp
        )
        signUpScreen(
            navigateToSignIn = authState::navigateToSignIn
        )
    }
}