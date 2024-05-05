package com.tynkovski.apps.messenger.feature.auth.pages.signUp

import androidx.lifecycle.ViewModel
import com.tynkovski.apps.messenger.core.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

}