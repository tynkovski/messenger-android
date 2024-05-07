package com.tynkovski.apps.messenger.core.domain

import com.tynkovski.apps.messenger.core.data.repository.AuthRepository
import javax.inject.Inject

class RefreshTokenUsecase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(refreshToken: String) = authRepository.refreshToken(refreshToken)
}