package com.tynkovski.apps.messenger.core.data.repository.impl

import com.tynkovski.apps.messenger.core.data.Synchronizer
import com.tynkovski.apps.messenger.core.data.repository.UsersRepository
import com.tynkovski.apps.messenger.core.data.util.UserMapper
import com.tynkovski.apps.messenger.core.model.data.User
import com.tynkovski.apps.messenger.core.network.Dispatcher
import com.tynkovski.apps.messenger.core.network.MessengerDispatchers
import com.tynkovski.apps.messenger.core.network.UserDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class UsersRepositoryImpl @Inject constructor(
    private val network: UserDataSource,
    @Dispatcher(MessengerDispatchers.IO) private val dispatcher: CoroutineDispatcher,
) : UsersRepository {

    override fun getUser(): Flow<User> = flow {
        emit(network.getUser())
    }.map(UserMapper.responseToEntry).flowOn(dispatcher)

    override fun getUser(id: Long): Flow<User> = flow {
        emit(network.getUser(id))
    }.map(UserMapper.responseToEntry).flowOn(dispatcher)

    override fun editUser(name: String?, image: String?): Flow<User> = flow {
        emit(network.editUser(name, image))
    }.map(UserMapper.responseToEntry).flowOn(dispatcher)

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean = true // todo add sync
}