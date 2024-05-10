package com.tynkovski.apps.messenger.core.data.repository.impl

import com.tynkovski.apps.messenger.core.data.Synchronizer
import com.tynkovski.apps.messenger.core.data.repository.RoomsRepository
import com.tynkovski.apps.messenger.core.data.util.RoomMapper
import com.tynkovski.apps.messenger.core.database.dao.RoomsDao
import com.tynkovski.apps.messenger.core.model.data.Room
import com.tynkovski.apps.messenger.core.network.Dispatcher
import com.tynkovski.apps.messenger.core.network.MessengerDispatchers
import com.tynkovski.apps.messenger.core.network.RoomsDataSource
import com.tynkovski.apps.messenger.core.network.util.offlineFirst
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class RoomsRepositoryImpl @Inject constructor(
    private val network: RoomsDataSource,
    private val dao: RoomsDao,
    @Dispatcher(MessengerDispatchers.IO) private val dispatcher: CoroutineDispatcher,
) : RoomsRepository {

    override fun observeRooms(): Flow<List<Room>> =
        dao.getRooms().map(RoomMapper.localListToEntryList)

    override fun createRoom(
        collocutorId: Long,
        name: String?,
        image: String?,
    ): Flow<Room> = offlineFirst(
        getEntity = { null },
        getResponse = {
            network.createRoom(
                collocutorId = collocutorId,
                name = null,
                image = null
            )
        },
        saveToDatabase = { dao.upsert(it) },
        localToEntryMapper = RoomMapper.localToEntry,
        remoteToEntryMapper = RoomMapper.remoteToEntry,
        entryToLocalMapper = RoomMapper.entryToLocal
    ).flowOn(dispatcher)

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean = true
}