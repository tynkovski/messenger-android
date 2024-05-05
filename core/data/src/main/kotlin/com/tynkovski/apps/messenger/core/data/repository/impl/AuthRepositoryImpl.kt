package com.tynkovski.apps.messenger.core.data.repository.impl

import com.tynkovski.apps.messenger.core.data.repository.AuthRepository
import com.tynkovski.apps.messenger.core.datastore.TokenHolder
import com.tynkovski.apps.messenger.core.model.data.AccessToken
import com.tynkovski.apps.messenger.core.model.data.Token
import com.tynkovski.apps.messenger.core.network.AuthDataSource
import com.tynkovski.apps.messenger.core.network.Dispatcher
import com.tynkovski.apps.messenger.core.network.MessengerDispatchers
import com.tynkovski.apps.messenger.core.network.model.asExternalModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    @Dispatcher(MessengerDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val tokenHolder: TokenHolder,
    private val network: AuthDataSource
) : AuthRepository {
    override fun signUp(name: String?, login: String, password: String): Flow<Token> = flow {
        val response = network.signUp(name, login, password)
        val model = response.asExternalModel()
        tokenHolder.setToken(model.accessToken, model.refreshToken)
        emit(model)
    }.flowOn(ioDispatcher)

    override fun signIn(login: String, password: String): Flow<Token> = flow {
        val response = network.signIn(login, password)
        val model = response.asExternalModel()
        tokenHolder.setToken(model.accessToken, model.refreshToken)
        emit(model)
    }.flowOn(ioDispatcher)

    override fun refreshToken(refreshToken: String): Flow<AccessToken> = flow {
        val response = network.refreshToken(refreshToken)
        val model = response.asExternalModel()
        tokenHolder.setAccessToken(model.accessToken)
        emit(model)
    }.flowOn(ioDispatcher)

    override fun logout(refreshToken: String): Flow<Unit> = flow {
        network.logout(refreshToken)
        tokenHolder.logout()
        emit(Unit)
    }.flowOn(ioDispatcher)
}