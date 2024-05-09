package com.tynkovski.apps.messenger.core.data.repository.impl

import com.tynkovski.apps.messenger.core.data.Synchronizer
import com.tynkovski.apps.messenger.core.data.repository.RoomsRepository
import com.tynkovski.apps.messenger.core.data.util.roomMapper
import com.tynkovski.apps.messenger.core.model.data.Room
import com.tynkovski.apps.messenger.core.network.Dispatcher
import com.tynkovski.apps.messenger.core.network.MessengerDispatchers
import com.tynkovski.apps.messenger.core.network.RoomsDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class RoomsRepositoryImpl @Inject constructor(
    private val roomsDataSource: RoomsDataSource,
    @Dispatcher(MessengerDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : RoomsRepository {

    override fun createRoom(collocutorId: Long): Flow<Room> = flow {
        emit(roomsDataSource.createRoom(collocutorId))
    }.map(roomMapper).flowOn(ioDispatcher)

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean = true // todo add sync
}