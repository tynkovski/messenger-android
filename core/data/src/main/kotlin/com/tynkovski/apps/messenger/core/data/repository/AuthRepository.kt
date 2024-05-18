package com.tynkovski.apps.messenger.core.data.repository

import com.tynkovski.apps.messenger.core.model.data.AccessToken
import com.tynkovski.apps.messenger.core.model.data.Token
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun signUp(name: String?, login: String, password: String): Flow<Token>
    fun signIn(login: String, password: String): Flow<Token>
    fun refreshToken(refreshToken: String): Flow<AccessToken>
    fun logout(refreshToken: String): Flow<Unit>
}