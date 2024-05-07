package com.tynkovski.apps.messenger.core.data.repository.impl

import com.tynkovski.apps.messenger.core.data.Synchronizer
import com.tynkovski.apps.messenger.core.data.repository.UsersRepository
import com.tynkovski.apps.messenger.core.data.util.timeFormatter
import com.tynkovski.apps.messenger.core.model.Result
import com.tynkovski.apps.messenger.core.model.data.User
import com.tynkovski.apps.messenger.core.model.toResult
import com.tynkovski.apps.messenger.core.network.Dispatcher
import com.tynkovski.apps.messenger.core.network.MessengerDispatchers
import com.tynkovski.apps.messenger.core.network.UserDataSource
import com.tynkovski.apps.messenger.core.network.model.UserResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDateTime
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val network : UserDataSource,
    @Dispatcher(MessengerDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : UsersRepository {
    private val userMapper: (UserResponse) -> User = {
        User(
            id = it.id,
            login = it.login,
            name = it.name,
            image = it.image,
            createdAt = LocalDateTime.parse(it.createdAt, timeFormatter),
            isDeleted = it.isDeleted
        )
    }

    override fun getUser(): Flow<Result<User>> = flow {
        emit(network.getUser())
    }.toResult(userMapper).flowOn(ioDispatcher)

    override fun getUser(id: Long): Flow<Result<User>> = flow {
        emit(network.getUser(id))
    }.toResult(userMapper).flowOn(ioDispatcher)

    override fun editUser(name: String?, image: String?): Flow<Result<User>> = flow {
        emit(network.editUser(name, image))
    }.toResult(userMapper).flowOn(ioDispatcher)

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean = true // todo add sync
}