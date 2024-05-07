package com.tynkovski.apps.messenger.feature.auth.pages.signIn

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tynkovski.apps.messenger.core.designsystem.component.ButtonState
import com.tynkovski.apps.messenger.core.domain.LoginUserUsecase
import com.tynkovski.apps.messenger.core.model.onError
import com.tynkovski.apps.messenger.core.model.onLoading
import com.tynkovski.apps.messenger.core.model.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val loginUserUsecase: LoginUserUsecase,
) : ViewModel() {
    private val _buttonState = MutableStateFlow(ButtonState.Success)

    private val _loginInputState = MutableStateFlow("")
    private val _passwordInputState = MutableStateFlow("")

    fun setPasswordInputState(value: String) { _passwordInputState.value = value }
    fun setLoginInputState(value: String) { _loginInputState.value = value }

    fun signIn() {
        viewModelScope.launch {
            val login = _loginInputState.value
            val password = _passwordInputState.value
            loginUserUsecase(login, password)
                .onLoading {
                    Log.d("SignInViewModel.signIn", "Loading")
                    _buttonState.value = ButtonState.Loading
                }
                .onSuccess {
                    Log.d("SignInViewModel.signIn", "${it.javaClass.simpleName} $it")
                    _buttonState.value = ButtonState.Success
                }
                .onError {
                    Log.d("SignInViewModel.signIn", "${it.javaClass.simpleName} $it")
                    _buttonState.value = ButtonState.Error
                }

                .collect()
        }
    }

    val buttonState: StateFlow<ButtonState> = _buttonState.asStateFlow()

    val loginInputState: StateFlow<String> = _loginInputState.asStateFlow()
    val passwordInputState: StateFlow<String> = _passwordInputState.asStateFlow()
}