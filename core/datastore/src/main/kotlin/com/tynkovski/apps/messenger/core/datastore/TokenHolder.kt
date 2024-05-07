package com.tynkovski.apps.messenger.core.datastore

import com.tynkovski.apps.messenger.core.model.data.Token
import kotlinx.coroutines.flow.Flow

interface TokenHolder {
    suspend fun setAccessToken(accessToken: String)
    suspend fun setRefreshToken(refreshToken: String)
    suspend fun setToken(accessToken: String, refreshToken: String)
    suspend fun logout()
    fun getTokenFlow(): Flow<Token>
    fun getToken(): Token?
}