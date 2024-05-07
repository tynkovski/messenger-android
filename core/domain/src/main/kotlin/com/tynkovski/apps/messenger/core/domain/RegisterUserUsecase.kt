package com.tynkovski.apps.messenger.core.domain

import com.tynkovski.apps.messenger.core.data.repository.AuthRepository
import javax.inject.Inject

class RegisterUserUsecase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(name: String?, login: String, password: String) =
        authRepository.signUp(name, login, password)
}