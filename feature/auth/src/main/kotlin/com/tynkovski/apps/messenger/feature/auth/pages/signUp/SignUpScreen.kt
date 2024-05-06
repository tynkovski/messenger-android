package com.tynkovski.apps.messenger.feature.auth.pages.signUp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

private const val NAME_TAB = 0
private const val LOGIN_TAB = 1
private const val PASSWORD_TAB = 2
private const val TAB_COUNT = 3

@Composable
internal fun SignUpRoute(
    navigateToSignIn: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel(),
) {
    SignUpScreen(
        modifier = modifier,
        navigateToSignIn = navigateToSignIn
    )
}

@Composable
internal fun SignUpScreen(
    navigateToSignIn: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = navigateToSignIn
        ) {
            Text("to sign in")
        }
    }
}