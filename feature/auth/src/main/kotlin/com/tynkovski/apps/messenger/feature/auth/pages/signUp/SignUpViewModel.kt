package com.tynkovski.apps.messenger.feature.auth.pages.signUp

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tynkovski.apps.messenger.core.designsystem.component.ButtonState
import com.tynkovski.apps.messenger.core.domain.RegisterUserUsecase
import com.tynkovski.apps.messenger.core.model.collector
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val BUTTON_STATE = "button_state"
private const val LOGIN_INPUT = "login_input"
private const val NAME_INPUT = "name_input"
private const val PASSWORD_INPUT = "password_input"
private const val PASSWORD_REPEAT_INPUT = "password_repeat_input"

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val registerUserUsecase: RegisterUserUsecase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val buttonState = savedStateHandle.getStateFlow(BUTTON_STATE, ButtonState.Success)
    val loginInputState = savedStateHandle.getStateFlow(LOGIN_INPUT, "")
    val nameInputState = savedStateHandle.getStateFlow(NAME_INPUT, "")
    val passwordInputState = savedStateHandle.getStateFlow(PASSWORD_INPUT, "")
    val passwordRepeatInputState = savedStateHandle.getStateFlow(PASSWORD_REPEAT_INPUT, "")

    private fun setButtonState(value: ButtonState) {
        Log.d("signUp.buttonState", "$value")
        savedStateHandle[BUTTON_STATE] = value
    }

    fun setPasswordInput(value: String) {
        savedStateHandle[PASSWORD_INPUT] = value
    }

    fun setPasswordRepeatInput(value: String) {
        savedStateHandle[PASSWORD_REPEAT_INPUT] = value
    }

    fun setLoginInput(value: String) {
        savedStateHandle[LOGIN_INPUT] = value
    }

    fun setNameInput(value: String) {
        savedStateHandle[NAME_INPUT] = value
    }

    fun signUp() {
        viewModelScope.launch {
            val name: String? = nameInputState.value.ifEmpty { null }
            val login: String = loginInputState.value
            val password: String = passwordInputState.value
            registerUserUsecase(name, login, password).collector(
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