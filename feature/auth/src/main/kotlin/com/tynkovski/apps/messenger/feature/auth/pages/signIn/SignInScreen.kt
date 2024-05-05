package com.tynkovski.apps.messenger.feature.auth.pages.signIn

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
internal fun SignInRoute(
    navigateToSignUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = hiltViewModel(),
) {
    val buttonState by viewModel.buttonState.collectAsStateWithLifecycle()
    SignInScreen(
        buttonState = buttonState,
        signIn = viewModel::signIn,
        navigateToSignUp = navigateToSignUp,
        modifier = modifier,
    )
}

@Composable
internal fun SignInScreen(
    buttonState: SignInViewModel.SignInButtonState,
    signIn: (String, String) -> Unit,
    navigateToSignUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Button(onClick = { signIn("vale", "testPassword123") }) {
                Text(text = buttonState.toString())
            }
            Button(onClick = navigateToSignUp) {
                Text("to sign up")
            }
        }

    }
}