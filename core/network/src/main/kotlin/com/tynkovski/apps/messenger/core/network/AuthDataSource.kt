package com.tynkovski.apps.messenger.core.network

import com.tynkovski.apps.messenger.core.NetResult
import com.tynkovski.apps.messenger.core.network.model.AccessResponse
import com.tynkovski.apps.messenger.core.network.model.ErrorResponse
import com.tynkovski.apps.messenger.core.network.model.TokenResponse

interface AuthDataSource {
    suspend fun signUp(name: String?, login: String, password: String): NetResult<TokenResponse, ErrorResponse>
    suspend fun signIn(login: String, password: String): NetResult<TokenResponse, ErrorResponse>
    suspend fun refreshToken(refreshToken: String): NetResult<AccessResponse, ErrorResponse>
    suspend fun logout(refreshToken: String): NetResult<Unit, ErrorResponse>
}