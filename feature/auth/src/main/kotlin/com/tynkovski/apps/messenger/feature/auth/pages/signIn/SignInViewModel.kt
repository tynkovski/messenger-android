package com.tynkovski.apps.messenger.feature.auth.pages.signIn

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tynkovski.apps.messenger.core.designsystem.component.ButtonState
import com.tynkovski.apps.messenger.core.domain.LoginUserUsecase
import com.tynkovski.apps.messenger.core.model.collector
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val BUTTON_STATE = "button_state"
private const val LOGIN_INPUT = "login_input"
private const val PASSWORD_INPUT = "password_input"

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val loginUserUsecase: LoginUserUsecase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val buttonState = savedStateHandle.getStateFlow(BUTTON_STATE, ButtonState.Success)

    val loginInputState = savedStateHandle.getStateFlow(LOGIN_INPUT, "")
    val passwordInputState = savedStateHandle.getStateFlow(PASSWORD_INPUT, "")

    private fun setButtonState(value: ButtonState) {
        Log.d("signIn.buttonState", "$value")
        savedStateHandle[BUTTON_STATE] = value
    }

    fun setPasswordInput(value: String) {
        savedStateHandle[PASSWORD_INPUT] = value
    }

    fun setLoginInput(value: String) {
        savedStateHandle[LOGIN_INPUT] = value
    }

    fun signIn() {
        viewModelScope.launch {
            val login = loginInputState.value
            val password = passwordInputState.value
            loginUserUsecase(login, password).collector(
                onLoading = {
                    setButtonState(ButtonState.Loading)
                },
                onSuccess = {
                    setButtonState(ButtonState.Success)
                },
                onError = {
                    setButtonState(ButtonState.Error)
                }
            )
        }
    }
}