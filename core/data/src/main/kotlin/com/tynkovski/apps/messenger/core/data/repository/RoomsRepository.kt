package com.tynkovski.apps.messenger.core.data.repository

import androidx.paging.PagingData
import com.tynkovski.apps.messenger.core.data.Syncable
import com.tynkovski.apps.messenger.core.model.data.Room
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface RoomsRepository : Syncable {
    val isConnected: StateFlow<Boolean>

    fun startWebsocket()

    fun stopWebsocket()

    fun getPagingRooms(): Flow<PagingData<Room>>

    fun findRoom(
        collocutorId: Long,
    ): Flow<Room>

    fun getRoom(
        roomId: Long,
    ): Flow<Room>
}