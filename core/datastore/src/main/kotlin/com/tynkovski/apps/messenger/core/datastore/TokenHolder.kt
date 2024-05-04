package com.tynkovski.apps.messenger.core.datastore

interface TokenHolder {
    suspend fun getAccessToken(): String
    suspend fun getRefreshToken(): String
    suspend fun setAccessToken(accessToken: String)
    suspend fun setRefreshToken(refreshToken: String)
    suspend fun logout()
}