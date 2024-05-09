package com.tynkovski.apps.messenger.core.data.repository.impl

import com.tynkovski.apps.messenger.core.data.repository.AuthRepository
import com.tynkovski.apps.messenger.core.data.util.accessMapper
import com.tynkovski.apps.messenger.core.data.util.tokenMapper
import com.tynkovski.apps.messenger.core.data.util.unitMapper
import com.tynkovski.apps.messenger.core.datastore.TokenHolder
import com.tynkovski.apps.messenger.core.model.data.AccessToken
import com.tynkovski.apps.messenger.core.model.data.Token
import com.tynkovski.apps.messenger.core.network.AuthDataSource
import com.tynkovski.apps.messenger.core.network.Dispatcher
import com.tynkovski.apps.messenger.core.network.MessengerDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val tokenHolder: TokenHolder,
    private val network: AuthDataSource,
    @Dispatcher(MessengerDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : AuthRepository {
    override fun signUp(name: String?, login: String, password: String): Flow<Token> =
        flow {
            val response = network.signUp(name, login, password)
            tokenHolder.setToken(response.accessToken, response.refreshToken)
            emit(response)
        }.map(tokenMapper).flowOn(ioDispatcher)

    override fun signIn(login: String, password: String): Flow<Token> = flow {
        val response = network.signIn(login, password)
        tokenHolder.setToken(response.accessToken, response.refreshToken)
        emit(response)
    }.map(tokenMapper).flowOn(ioDispatcher)

    override fun refreshToken(refreshToken: String): Flow<AccessToken> = flow {
        val response = network.refreshToken(refreshToken)
        tokenHolder.setAccessToken(response.accessToken)
        emit(response)
    }.map(accessMapper).flowOn(ioDispatcher)

    override fun logout(refreshToken: String): Flow<Unit> = flow {
        val response = network.logout(refreshToken)
        tokenHolder.logout()
        emit(response)
    }.map(unitMapper).flowOn(ioDispatcher)
}