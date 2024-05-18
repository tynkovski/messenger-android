package com.tynkovski.apps.messenger.core.datastore.impl

import com.tynkovski.apps.messenger.core.datastore.MessengerPreferencesDataSource
import com.tynkovski.apps.messenger.core.datastore.TokenHolder
import com.tynkovski.apps.messenger.core.model.data.Token
import com.tynkovski.apps.messenger.core.network.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class TokenHolderImpl @Inject constructor(
    private val messengerPreferencesDataSource: MessengerPreferencesDataSource,
) : TokenHolder {

    override fun getToken() = runBlocking {
        messengerPreferencesDataSource
            .tokenPreferencesFlow
            .map { Token(it.accessToken, it.refreshToken) }
            .firstOrNull()
    }

    override suspend fun setAccessToken(accessToken: String) {
        messengerPreferencesDataSource.setAccessToken(accessToken)
    }

    override suspend fun setRefreshToken(refreshToken: String) {
        messengerPreferencesDataSource.setRefreshToken(refreshToken)
    }

    override suspend fun setToken(accessToken: String, refreshToken: String) {
        messengerPreferencesDataSource.setAccessToken(accessToken)
        messengerPreferencesDataSource.setRefreshToken(refreshToken)
    }

    override suspend fun logout() {
        messengerPreferencesDataSource.clear()
    }

    override fun getTokenFlow(): Flow<Token> {
        return messengerPreferencesDataSource.tokenPreferencesFlow.map {
            Token(it.accessToken, it.refreshToken)
        }
    }
}