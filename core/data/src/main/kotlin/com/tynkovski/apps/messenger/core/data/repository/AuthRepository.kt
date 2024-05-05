package com.tynkovski.apps.messenger.core.data.repository

import com.tynkovski.apps.messenger.core.Result
import com.tynkovski.apps.messenger.core.model.data.AccessToken
import com.tynkovski.apps.messenger.core.model.data.Token
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun signUp(name: String?, login: String, password: String): Flow<Result<Token>>
    fun signIn(login: String, password: String): Flow<Result<Token>>
    fun refreshToken(refreshToken: String): Flow<Result<AccessToken>>
    fun logout(refreshToken: String): Flow<Result<Unit>>
}