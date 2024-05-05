package com.tynkovski.apps.messenger.feature.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tynkovski.apps.messenger.feature.auth.pages.signIn.SignInRoute
import com.tynkovski.apps.messenger.feature.auth.pages.signUp.SignUpRoute

const val AUTH_ROUTE_SIGN_IN = "auth_route_sign_in"
const val AUTH_ROUTE_SIGN_UP = "auth_route_sign_up"

fun NavController.navigateToAuthSignIn() =
    navigate(AUTH_ROUTE_SIGN_IN) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

fun NavController.navigateToAuthSignUp() =
    navigate(AUTH_ROUTE_SIGN_UP) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

fun NavGraphBuilder.signInScreen(
    navigateToSignUp: () -> Unit
) {
    composable(
        route = AUTH_ROUTE_SIGN_IN,
        arguments = listOf(),
    ) {
        SignInRoute(navigateToSignUp = navigateToSignUp)
    }
}

fun NavGraphBuilder.signUpScreen(
    navigateToSignIn: () -> Unit
) {
    composable(
        route = AUTH_ROUTE_SIGN_UP,
        arguments = listOf(),
    ) {
        SignUpRoute(navigateToSignIn = navigateToSignIn)
    }
}

