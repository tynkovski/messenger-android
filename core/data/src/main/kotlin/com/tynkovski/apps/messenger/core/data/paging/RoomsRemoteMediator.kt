@file:OptIn(ExperimentalPagingApi::class)

package com.tynkovski.apps.messenger.core.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.tynkovski.apps.messenger.core.data.util.RoomMapper
import com.tynkovski.apps.messenger.core.database.MessengerDatabase
import com.tynkovski.apps.messenger.core.database.dao.RoomsDao
import com.tynkovski.apps.messenger.core.database.model.RoomEntity
import com.tynkovski.apps.messenger.core.network.RoomsDataSource

class RoomsRemoteMediator(
    private val db: MessengerDatabase,
    private val dao: RoomsDao,
    private val network: RoomsDataSource
) : RemoteMediator<Int, RoomEntity>() {
    private var currentPage = 0L

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RoomEntity>
    ): MediatorResult {
        try {
            val page = when (loadType) {
                LoadType.REFRESH -> 0L
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        0L
                    } else {
                        currentPage + 1
                    }
                }
            }
            val response = network.getRoomsPaged(page, state.config.pageSize)
            val rooms = RoomMapper.remoteListToEntryList(response.rooms)
            val entities = RoomMapper.entryListToLocalList(rooms)

            currentPage = page

            db.withTransaction {
                dao.upsert(entities)
            }

            return MediatorResult.Success(endOfPaginationReached = rooms.isEmpty())
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }
}