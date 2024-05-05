package com.tynkovski.apps.messenger.core.domain

import com.tynkovski.apps.messenger.core.data.repository.AuthRepository
import javax.inject.Inject

class LoginUserUsecase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(login: String, password: String) = authRepository.signIn(login, password)

}