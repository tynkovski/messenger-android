package com.tynkovski.apps.messenger.feature.auth.pages.signUp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val _buttonState = MutableStateFlow(SignInButtonState.Success)

    fun signUp(name: String?, login: String, password: String) {
        viewModelScope.launch {
            registerUserUsecase(name, login, password)
                .onSuccess {
                    Log.d("SignUpViewModel.signIn", "${it.javaClass.simpleName} $it")
                    _buttonState.value = SignInButtonState.Success
                }
                .onError {
                    Log.d("SignUpViewModel.signIn", "${it.javaClass.simpleName} $it")
                    _buttonState.value = SignInButtonState.Error
                }
                .onLoading {
                    Log.d("SignUpViewModel.signIn", "Loading")
                    _buttonState.value = SignInButtonState.Loading
                }
                .collect()
        }
    }

    val buttonState: StateFlow<SignInButtonState> = _buttonState.asStateFlow()

    enum class SignInButtonState { Loading, Success, Error }
}