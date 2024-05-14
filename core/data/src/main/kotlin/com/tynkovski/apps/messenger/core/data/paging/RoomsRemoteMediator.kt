@file:OptIn(ExperimentalPagingApi::class)

package com.tynkovski.apps.messenger.core.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.tynkovski.apps.messenger.core.data.util.RoomMapper
import com.tynkovski.apps.messenger.core.database.dao.RoomsDao
import com.tynkovski.apps.messenger.core.model.data.Room
import com.tynkovski.apps.messenger.core.network.RoomsDataSource

class RoomsRemoteMediator(
    private val roomsDao: RoomsDao,
    private val network: RoomsDataSource
) : RemoteMediator<Int, Room>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Room>
    ): MediatorResult {
        try {
            val page = when (loadType) {
                LoadType.REFRESH -> 0 // todo 0 or null?
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    lastItem?.id ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            val response = network.getRoomsPaged(page, PAGE_SIZE)
            val rooms = RoomMapper.remoteListToEntryList(response.rooms)
            val entities = RoomMapper.entryListToLocalList(rooms)

            if (loadType == LoadType.REFRESH) {
                Log.d("RoomsRemoteMediator", "Refresh")
                // roomsDao.deleteRooms(entities)
            }

            roomsDao.upsert(entities)

            val endOfPaginationReached = rooms.isEmpty()
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    companion object {
        const val PAGE_SIZE = 10
    }
}