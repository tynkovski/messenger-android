package com.tynkovski.apps.messenger.feature.auth.pages.signUp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tynkovski.apps.messenger.core.designsystem.component.ButtonState
import com.tynkovski.apps.messenger.core.domain.RegisterUserUsecase
import com.tynkovski.apps.messenger.core.onError
import com.tynkovski.apps.messenger.core.onLoading
import com.tynkovski.apps.messenger.core.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val registerUserUsecase: RegisterUserUsecase,
) : ViewModel() {
    private val _buttonState = MutableStateFlow(ButtonState.Success)

    private val _loginInputState = MutableStateFlow("")
    private val _nameInputState = MutableStateFlow("")
    private val _passwordInputState = MutableStateFlow("")
    private val _passwordRepeatInputState = MutableStateFlow("")

    fun setPasswordInputState(value: String) { _passwordInputState.value = value }
    fun setPasswordRepeatInputState(value: String) { _passwordRepeatInputState.value = value }
    fun setLoginInputState(value: String) { _loginInputState.value = value }
    fun setNameInputState(value: String) { _nameInputState.value = value }

    fun signUp() {
        viewModelScope.launch {
            val name: String? = _nameInputState.value.ifEmpty { null }
            val login: String = _loginInputState.value
            val password: String = _passwordInputState.value
            registerUserUsecase(name, login, password)
                .onSuccess {
                    Log.d("SignUpViewModel.signIn", "${it.javaClass.simpleName} $it")
                    _buttonState.value = ButtonState.Success
                }
                .onError {
                    Log.d("SignUpViewModel.signIn", "${it.javaClass.simpleName} $it")
                    _buttonState.value = ButtonState.Error
                }
                .onLoading {
                    Log.d("SignUpViewModel.signIn", "Loading")
                    _buttonState.value = ButtonState.Loading
                }
                .collect()
        }
    }

    val buttonState: StateFlow<ButtonState> = _buttonState.asStateFlow()

    val loginInputState: StateFlow<String> = _loginInputState.asStateFlow()
    val nameInputState: StateFlow<String> = _nameInputState.asStateFlow()
    val passwordInputState: StateFlow<String> = _passwordInputState.asStateFlow()
    val passwordRepeatInputState: StateFlow<String> = _passwordRepeatInputState.asStateFlow()
}