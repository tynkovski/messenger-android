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
import com.tynkovski.apps.messenger.feature.auth.navigation.AUTH_ROUTE_SIGN_IN
import com.tynkovski.apps.messenger.feature.auth.navigation.AUTH_ROUTE_SIGN_UP
import com.tynkovski.apps.messenger.feature.auth.navigation.navigateToAuthSignIn
import com.tynkovski.apps.messenger.feature.auth.navigation.navigateToAuthSignUp
import com.tynkovski.apps.messenger.navigation.AuthDestination

@Composable
fun rememberMessengerAuthState(
    navController: NavHostController = rememberNavController(),
): MessengerAuthState {
    return remember(navController) {
        MessengerAuthState(navController = navController)
    }
}

@Stable
class MessengerAuthState(val navController: NavHostController) {


    val destinations: List<AuthDestination> = AuthDestination.entries

    val currentTopLevelDestination: AuthDestination?
        @Composable get() = when (currentDestination?.route) {
            AUTH_ROUTE_SIGN_IN -> AuthDestination.SignIn
            AUTH_ROUTE_SIGN_UP -> AuthDestination.SignUp
            else -> null
        }

    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    fun navigateToDestination(authDestination: AuthDestination) {
        trace("Navigation: ${authDestination.name}") {
            when (authDestination) {
                AuthDestination.SignIn -> navController.navigateToAuthSignIn()
                AuthDestination.SignUp -> navController.navigateToAuthSignUp()
            }
        }
    }

    fun navigateToSignUp() = navController.navigateToAuthSignUp()
    fun navigateToSignIn() = navController.navigateToAuthSignIn()
}
