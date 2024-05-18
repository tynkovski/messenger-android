@file:OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)

package com.tynkovski.apps.messenger.feature.auth.pages.signUp

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
private const val NAME_TAB = 1
private const val PASSWORD_TAB = 2
private const val TAB_COUNT = 3

@Composable
internal fun SignUpRoute(
    navigateToSignIn: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel(),
) {
    val buttonState by viewModel.buttonState.collectAsStateWithLifecycle()

    val login by viewModel.loginInputState.collectAsStateWithLifecycle()
    val name by viewModel.nameInputState.collectAsStateWithLifecycle()
    val password by viewModel.passwordInputState.collectAsStateWithLifecycle()
    val passwordRepeat by viewModel.passwordRepeatInputState.collectAsStateWithLifecycle()

    SignUpScreen(
        login = login,
        loginChanged = viewModel::setLoginInput,
        name = name,
        nameChanged = viewModel::setNameInput,
        password = password,
        passwordChanged = viewModel::setPasswordInput,
        passwordRepeat = passwordRepeat,
        passwordRepeatChanged = viewModel::setPasswordRepeatInput,
        buttonState = buttonState,
        signUpButtonClick = viewModel::signUp,
        navigateToSignIn = navigateToSignIn,
        modifier = modifier
    )
}

@Composable
internal fun SignUpScreen(
    login: String,
    loginChanged: (String) -> Unit,
    name: String,
    nameChanged: (String) -> Unit,
    password: String,
    passwordChanged: (String) -> Unit,
    passwordRepeat: String,
    passwordRepeatChanged: (String) -> Unit,
    buttonState: ButtonState,
    signUpButtonClick: () -> Unit,
    navigateToSignIn: () -> Unit,
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
                    navigateToSignIn = navigateToSignIn,
                    toNextPageClick = ::nextPage
                )
            }

            NAME_TAB -> {
                NameTab(
                    modifier = modifier,
                    requestFocus = keyboardVisible,
                    name = name,
                    nameChanged = nameChanged,
                    toPreviousPageClick = ::previousPage,
                    toNextPageClick = ::nextPage,
                )
            }

            PASSWORD_TAB -> {
                PasswordTab(
                    modifier = modifier,
                    buttonState = buttonState,
                    requestFocus = keyboardVisible,
                    password = password,
                    passwordChanged = passwordChanged,
                    passwordRepeat = passwordRepeat,
                    passwordRepeatChanged = passwordRepeatChanged,
                    signUpButtonClick = signUpButtonClick,
                    toPreviousPageClick = ::previousPage
                )
            }
        }
    }
}

@Composable
private fun LoginTab(
    modifier: Modifier,
    login: String,
    loginChanged: (String) -> Unit,
    navigateToSignIn: () -> Unit,
    toNextPageClick: () -> Unit,
    requestFocus: Boolean
) = SignBase(
    modifier = modifier,
    showIcon = false,
    backClick = { }
) {
    BackHandler(enabled = !requestFocus, onBack = navigateToSignIn)

    val widthModifier = Modifier.fillMaxWidth()
    val paddingModifier = widthModifier.padding(horizontal = 16.dp)

    Spacer(
        modifier = Modifier
            .weight(1f)
            .heightIn(min = 56.dp)
    )

    Text(
        modifier = paddingModifier,
        style = MaterialTheme.typography.displaySmall.copy(
            fontWeight = FontWeight.Thin,
        ),
        text = stringResource(R.string.feature_auth_sign_up_login_title),
        textAlign = TextAlign.Center,
    )

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
            text = stringResource(R.string.feature_auth_sign_up_login_description),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.outline,
        )

        Spacer(modifier = Modifier.height(8.dp))

        val supportTextDefault = stringResource(
            R.string.feature_auth_sign_up_login_field_description
        )
        val supportTextLength = stringResource(
            R.string.feature_auth_sign_up_login_field_description_length
        )

        fun getSupportText(value: String): String? {
            if (value.isEmpty()) return null
            if (!Regex(".{2,16}").matches(value)) return supportTextLength

            return if (Regex("[a-zA-Z0-9]+").matches(value)) null else supportTextDefault
        }

        val supportText by remember(login) {
            derivedStateOf { getSupportText(login) }
        }

        AuthField(
            modifier = widthModifier,
            value = login,
            onValueChange = loginChanged,
            hint = stringResource(R.string.feature_auth_text_login),
            leading = MessengerIcons.PersonSearch,
            supportText = supportText,
            requestFocus = requestFocus
        )

        Spacer(modifier = Modifier.height(28.dp))

        val enabledButton by remember(login) {
            derivedStateOf { login.isNotEmpty() && getSupportText(login) == null }
        }

        DefaultButton(
            modifier = widthModifier,
            onClick = toNextPageClick,
            text = stringResource(R.string.feature_auth_sign_up_login_button),
            enabled = enabledButton,
        )
        Text(
            modifier = widthModifier,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.outline,
            text = stringResource(R.string.feature_auth_text_variant),
            textAlign = TextAlign.Center
        )
        DefaultOutlinedButton(
            modifier = widthModifier,
            onClick = navigateToSignIn,
            text = stringResource(R.string.feature_auth_button_sign_ip),
            enabled = true
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun NameTab(
    modifier: Modifier,
    name: String,
    nameChanged: (String) -> Unit,
    toNextPageClick: () -> Unit,
    toPreviousPageClick: () -> Unit,
    requestFocus: Boolean
) = SignBase(
    modifier = modifier,
    showIcon = true,
    backClick = toPreviousPageClick
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
            text = stringResource(R.string.feature_auth_sign_up_name_title),
        )

        Text(
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Light
            ),
            text = stringResource(R.string.feature_auth_sign_up_name_title_second),
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
            text = stringResource(R.string.feature_auth_sign_up_name_description),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.outline
        )

        Spacer(modifier = Modifier.height(8.dp))

        val supportTextDefault =
            stringResource(R.string.feature_auth_sign_up_name_field_description)

        val supportText by remember(name) {
            derivedStateOf { if (name.isNotEmpty()) null else supportTextDefault }
        }

        AuthField(
            modifier = widthModifier,
            value = name,
            onValueChange = nameChanged,
            hint = stringResource(R.string.feature_auth_text_name),
            leading = MessengerIcons.Person,
            requestFocus = requestFocus,
            supportText = supportText
        )

        Spacer(modifier = Modifier.height(28.dp))

        DefaultButton(
            modifier = widthModifier,
            onClick = toNextPageClick,
            text = stringResource(R.string.feature_auth_sign_up_name_button),
            enabled = true,
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}


@Composable
private fun PasswordTab(
    modifier: Modifier,
    password: String,
    passwordRepeat: String,
    passwordChanged: (String) -> Unit,
    passwordRepeatChanged: (String) -> Unit,
    toPreviousPageClick: () -> Unit,
    signUpButtonClick: () -> Unit,
    requestFocus: Boolean,
    buttonState: ButtonState
) = SignBase(
    modifier = modifier,
    showIcon = true,
    backClick = toPreviousPageClick
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
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Thin,
            ),
            text = stringResource(R.string.feature_auth_sign_up_password_title),
            textAlign = TextAlign.End
        )

        Text(
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Light),
            text = stringResource(R.string.feature_auth_sign_up_password_title_second),
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
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Light),
            text = stringResource(R.string.feature_auth_sign_up_password_description),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.outline,
        )

        Spacer(modifier = Modifier.height(8.dp))
    }

    val supportTextSize = stringResource(
        R.string.feature_auth_sign_up_password_field_description_size
    )
    val supportTextDigits = stringResource(
        R.string.feature_auth_sign_up_password_field_description_digits
    )
    val supportTextCapitalized = stringResource(
        R.string.feature_auth_sign_up_password_field_description_capitalized
    )
    val supportTextWhitespaces = stringResource(
        R.string.feature_auth_sign_up_password_field_description_whitespaces
    )
    val supportMismatch = stringResource(
        R.string.feature_auth_sign_up_password_field_description_mismatch
    )

    fun getSupportText(value: String): String? {
        if (value.isEmpty()) return null

        if (!Regex(".{8,32}").matches(value)) return supportTextSize
        if (Regex("\\D+").matches(value)) return supportTextDigits
        if (Regex("[^A-Z]+").matches(value)) return supportTextCapitalized
        if (!Regex("\\S+").matches(value)) return supportTextWhitespaces

        return null
    }

    val supportText by remember(password) {
        derivedStateOf { getSupportText(password) }
    }

    val (hiddenPasswordInput, hiddenPasswordInputChanged) = remember {
        mutableStateOf(true)
    }

    val visible = MessengerIcons.Visibility
    val invisible = MessengerIcons.VisibilityOff

    AuthField(
        modifier = paddingModifier,
        value = password,
        onValueChange = passwordChanged,
        hint = stringResource(R.string.feature_auth_text_password),
        leading = MessengerIcons.Lock,
        supportText = supportText,
        passwordInput = hiddenPasswordInput,
        trailing = if (hiddenPasswordInput) visible else invisible,
        onTrailingIconClick = {
            hiddenPasswordInputChanged(!hiddenPasswordInput)
        },
        requestFocus = requestFocus
    )

    Spacer(modifier = Modifier.height(12.dp))

    val (hiddenPasswordRepeatInput, hiddenPasswordRepeatInputChanged) = remember {
        mutableStateOf(true)
    }

    val passwordsAreEqual by remember(password, passwordRepeat) {
        derivedStateOf {
            password.isNotEmpty() && passwordRepeat.isNotEmpty() && password == passwordRepeat
        }
    }

    val repeatPasswordSupportText by remember(password, passwordRepeat) {
        derivedStateOf {
            if (passwordsAreEqual || passwordRepeat.isEmpty())
                null
            else
                supportMismatch
        }
    }

    AuthField(
        modifier = paddingModifier,
        value = passwordRepeat,
        onValueChange = passwordRepeatChanged,
        hint = stringResource(R.string.feature_auth_text_repeat_password),
        leading = MessengerIcons.Lock,
        passwordInput = hiddenPasswordRepeatInput,
        trailing = if (hiddenPasswordRepeatInput) visible else invisible,
        onTrailingIconClick = {
            hiddenPasswordRepeatInputChanged(!hiddenPasswordRepeatInput)
        },
        supportText = repeatPasswordSupportText,
    )

    Spacer(modifier = Modifier.height(28.dp))

    DefaultButton(
        modifier = paddingModifier,
        onClick = signUpButtonClick,
        text = stringResource(R.string.feature_auth_sign_up_password_button),
        enabled = passwordsAreEqual,
        state = buttonState
    )

    Spacer(modifier = Modifier.height(24.dp))
}