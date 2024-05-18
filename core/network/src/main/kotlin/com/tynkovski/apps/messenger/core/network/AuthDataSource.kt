package com.tynkovski.apps.messenger.core.network

import com.tynkovski.apps.messenger.core.network.model.response.AccessResponse
import com.tynkovski.apps.messenger.core.network.model.response.TokenResponse

interface AuthDataSource {
    suspend fun signUp(name: String?, login: String, password: String): TokenResponse
    suspend fun signIn(login: String, password: String): TokenResponse
    suspend fun refreshToken(refreshToken: String): AccessResponse
    suspend fun logout(refreshToken: String): Unit
}