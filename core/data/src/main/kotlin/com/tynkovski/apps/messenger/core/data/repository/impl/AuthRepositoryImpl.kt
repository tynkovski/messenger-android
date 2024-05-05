package com.tynkovski.apps.messenger.core.data.repository.impl

import com.tynkovski.apps.messenger.core.NetResult
import com.tynkovski.apps.messenger.core.Result
import com.tynkovski.apps.messenger.core.data.repository.AuthRepository
import com.tynkovski.apps.messenger.core.datastore.TokenHolder
import com.tynkovski.apps.messenger.core.model.data.AccessToken
import com.tynkovski.apps.messenger.core.model.data.Token
import com.tynkovski.apps.messenger.core.network.AuthDataSource
import com.tynkovski.apps.messenger.core.network.Dispatcher
import com.tynkovski.apps.messenger.core.network.MessengerDispatchers
import com.tynkovski.apps.messenger.core.network.model.AccessResponse
import com.tynkovski.apps.messenger.core.network.model.TokenResponse
import com.tynkovski.apps.messenger.core.toResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    @Dispatcher(MessengerDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val tokenHolder: TokenHolder,
    private val network: AuthDataSource,
) : AuthRepository {
    private val tokenMapper: (TokenResponse) -> Token = { Token(it.accessToken, it.refreshToken) }
    private val accessMapper: (AccessResponse) -> AccessToken = { AccessToken(it.accessToken) }
    private val unitMapper: (Unit) -> Unit = { Unit }

    override fun signUp(name: String?, login: String, password: String): Flow<Result<Token>> = flow {
        val netResult = network.signUp(name, login, password)
        if (netResult is NetResult.Success) {
            tokenHolder.setToken(netResult.value.accessToken, netResult.value.refreshToken)
        }
        emit(netResult)
    }.toResult(tokenMapper).flowOn(ioDispatcher)

    override fun signIn(login: String, password: String): Flow<Result<Token>> = flow {
        val netResult = network.signIn(login, password)
        if (netResult is NetResult.Success) {
            tokenHolder.setToken(netResult.value.accessToken, netResult.value.refreshToken)
        }
        emit(netResult)
    }.toResult(tokenMapper).flowOn(ioDispatcher)

    override fun refreshToken(refreshToken: String): Flow<Result<AccessToken>> = flow {
        val netResult = network.refreshToken(refreshToken)
        if (netResult is NetResult.Success) {
            tokenHolder.setAccessToken(netResult.value.accessToken)
        }
        emit(netResult)
    }.toResult(accessMapper).flowOn(ioDispatcher)

    override fun logout(refreshToken: String): Flow<Result<Unit>> = flow {
        val netResult = network.logout(refreshToken)
        if (netResult is NetResult.Success) {
            tokenHolder.logout()
        }
        emit(netResult)
    }.toResult(unitMapper).flowOn(ioDispatcher)
}