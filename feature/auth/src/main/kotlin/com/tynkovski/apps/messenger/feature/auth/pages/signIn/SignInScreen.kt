@file:OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)

package com.tynkovski.apps.messenger.feature.auth.pages.signIn

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tynkovski.apps.messenger.core.designsystem.component.AuthField
import com.tynkovski.apps.messenger.core.designsystem.component.ButtonState
import com.tynkovski.apps.messenger.core.designsystem.component.DefaultButton
import com.tynkovski.apps.messenger.core.designsystem.component.DefaultOutlinedButton
import com.tynkovski.apps.messenger.core.designsystem.icon.MessengerIcons
import com.tynkovski.apps.messenger.core.ui.SignBase
import com.tynkovski.apps.messenger.feature.auth.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val LOGIN_TAB = 0
private const val PASSWORD_TAB = 1
private const val TAB_COUNT = 2

@Composable
internal fun SignInRoute(
    navigateToSignUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = hiltViewModel(),
) {
    val buttonState by viewModel.buttonState.collectAsStateWithLifecycle()
    val login by viewModel.loginInputState.collectAsStateWithLifecycle()
    val password by viewModel.passwordInputState.collectAsStateWithLifecycle()

    SignInScreen(
        login = login,
        loginChanged = viewModel::setLoginInput,
        password = password,
        passwordChanged = viewModel::setPasswordInput,
        buttonState = buttonState,
        signInButtonClick = viewModel::signIn,
        navigateToSignUp = navigateToSignUp,
        modifier = modifier,
    )
}

@Composable
internal fun SignInScreen(
    login: String,
    loginChanged: (String) -> Unit,
    password: String,
    passwordChanged: (String) -> Unit,
    buttonState: ButtonState,
    signInButtonClick: () -> Unit,
    navigateToSignUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val keyboardVisible = WindowInsets.isImeVisible
    val pagerState: PagerState = rememberPagerState { TAB_COUNT }
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        state = pagerState,
        userScrollEnabled = false,
    ) {
        fun previousPage() {
            coroutineScope.launch { pagerState.scrollToPage(it - 1) }
        }

        fun nextPage() {
            coroutineScope.launch { pagerState.scrollToPage(it + 1) }
        }

        when (it) {
            LOGIN_TAB -> {
                LoginTab(
                    modifier = modifier,
                    requestFocus = keyboardVisible,
                    login = login,
                    loginChanged = loginChanged,
                    navigateToSignUp = navigateToSignUp,
                    toNextPageClick = ::nextPage
                )
            }

            PASSWORD_TAB -> {
                PasswordTab(
                    modifier = modifier,
                    buttonState = buttonState,
                    requestFocus = keyboardVisible,
                    password = password,
                    passwordChanged = passwordChanged,
                    signInButtonClick = signInButtonClick,
                    toPreviousPageClick = ::previousPage
                )
            }
        }
    }
}

@Composable
private fun LoginTab(
    requestFocus: Boolean,
    login: String,
    navigateToSignUp: () -> Unit,
    loginChanged: (String) -> Unit,
    toNextPageClick: () -> Unit,
    modifier: Modifier = Modifier,
) = SignBase(
    modifier = modifier,
    showIcon = false,
    backClick = {}
) {
    val widthModifier = Modifier.fillMaxWidth()
    val paddingModifier = widthModifier.padding(horizontal = 16.dp)

    Spacer(
        modifier = Modifier
            .heightIn(56.dp)
            .weight(1f)
    )

    Text(
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(horizontal = 16.dp),
        style = MaterialTheme.typography.displaySmall.copy(
            fontWeight = FontWeight.Thin,
        ),
        text = stringResource(R.string.feature_auth_title_page_1),
    )

    Spacer(
        modifier = Modifier
            .heightIn(56.dp)
            .weight(1f)
    )

    Column(
        modifier = paddingModifier,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = widthModifier,
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Light
            ),
            text = stringResource(R.string.feature_auth_description_login),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.outline
        )

        Spacer(modifier = Modifier.height(8.dp))

        AuthField(
            modifier = widthModifier,
            value = login,
            onValueChange = loginChanged,
            hint = stringResource(R.string.feature_auth_text_login),
            leading = MessengerIcons.PersonSearch,
            requestFocus = requestFocus
        )

        Spacer(modifier = Modifier.height(28.dp))

        val enabledButton by remember(login) {
            derivedStateOf { login.isNotEmpty() }
        }

        DefaultButton(
            modifier = widthModifier,
            onClick = toNextPageClick,
            text = stringResource(R.string.feature_auth_button_continue),
            enabled = enabledButton,
        )
        Text(
            modifier = widthModifier,
            style = MaterialTheme.typography.bodyLarge,
            text = stringResource(R.string.feature_auth_text_variant),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.outline
        )
        DefaultOutlinedButton(
            modifier = widthModifier,
            onClick = navigateToSignUp,
            text = stringResource(R.string.feature_auth_button_sign_up),
            enabled = true
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun PasswordTab(
    requestFocus: Boolean,
    password: String,
    buttonState: ButtonState,
    passwordChanged: (String) -> Unit,
    signInButtonClick: () -> Unit,
    toPreviousPageClick: () -> Unit,
    modifier: Modifier = Modifier,
) = SignBase(
    modifier = modifier,
    backClick = toPreviousPageClick,
    showIcon = true
) {
    val widthModifier = Modifier.fillMaxWidth()
    val paddingModifier = widthModifier.padding(horizontal = 16.dp)

    BackHandler(enabled = !requestFocus, onBack = toPreviousPageClick)

    Spacer(
        modifier = Modifier
            .weight(1f)
            .heightIn(min = 56.dp)
    )

    Column(
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(vertical = 56.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.End
    ) {
        Text(
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Thin,
            ),
            text = stringResource(R.string.feature_auth_title_page_2),
            textAlign = TextAlign.End
        )

        Text(
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Light),
            text = stringResource(R.string.feature_auth_description_page_2),
        )
    }

    Spacer(
        modifier = Modifier
            .weight(1f)
            .heightIn(min = 56.dp)
    )

    Column(
        modifier = paddingModifier,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = widthModifier,
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Light
            ),
            text = stringResource(R.string.feature_auth_description_password),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.outline
        )

        Spacer(modifier = Modifier.height(8.dp))

        val (hiddenInput, hiddenInputChanged) = remember {
            mutableStateOf(true)
        }
        val visible = MessengerIcons.Visibility
        val invisible = MessengerIcons.VisibilityOff

        AuthField(
            modifier = widthModifier,
            value = password,
            onValueChange = passwordChanged,
            hint = stringResource(R.string.feature_auth_text_password),
            passwordInput = hiddenInput,
            trailing = if (hiddenInput) visible else invisible,
            onTrailingIconClick = {
                hiddenInputChanged(!hiddenInput)
            },
            leading = MessengerIcons.Lock,
            requestFocus = requestFocus
        )

        Spacer(modifier = Modifier.height(28.dp))

        val enabledButton by remember(password) {
            derivedStateOf { password.isNotEmpty() }
        }

        DefaultButton(
            modifier = widthModifier,
            onClick = signInButtonClick,
            text = stringResource(R.string.feature_auth_button_enter),
            enabled = enabledButton,
            state = buttonState
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}