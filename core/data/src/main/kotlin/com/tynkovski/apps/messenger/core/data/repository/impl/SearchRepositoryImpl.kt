package com.tynkovski.apps.messenger.core.data.repository.impl

import com.tynkovski.apps.messenger.core.data.repository.SearchRepository
import com.tynkovski.apps.messenger.core.data.util.UserMapper
import com.tynkovski.apps.messenger.core.model.data.User
import com.tynkovski.apps.messenger.core.network.Dispatcher
import com.tynkovski.apps.messenger.core.network.MessengerDispatchers
import com.tynkovski.apps.messenger.core.network.SearchDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class SearchRepositoryImpl @Inject constructor(
    private val searchDataSource: SearchDataSource,
    @Dispatcher(MessengerDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : SearchRepository {
    override fun getUser(login: String): Flow<User> = flow {
        emit(searchDataSource.getUser(login))
    }.map(UserMapper.responseToEntry).flowOn(ioDispatcher)
}