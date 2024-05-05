package com.tynkovski.apps.messenger.core.datastore.impl

import com.tynkovski.apps.messenger.core.datastore.MessengerPreferencesDataSource
import com.tynkovski.apps.messenger.core.datastore.TokenHolder
import com.tynkovski.apps.messenger.core.model.data.Token
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TokenHolderImpl @Inject constructor(
    private val messengerPreferencesDataSource: MessengerPreferencesDataSource,
) : TokenHolder {
//    override suspend fun getAccessToken(): String {
//        val flow = messengerPreferencesDataSource
//            .tokenPreferencesFlow
//            .map { it.accessToken }
//        return flow.firstOrNull() ?: ""
//    }
//
//    override suspend fun getRefreshToken(): String {
//        val flow = messengerPreferencesDataSource
//            .tokenPreferencesFlow
//            .map { it.refreshToken }
//        return flow.firstOrNull() ?: ""
//    }

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