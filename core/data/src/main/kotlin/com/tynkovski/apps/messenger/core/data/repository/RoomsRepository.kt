package com.tynkovski.apps.messenger.core.data.repository

import androidx.paging.PagingData
import com.tynkovski.apps.messenger.core.data.Syncable
import com.tynkovski.apps.messenger.core.model.data.Room
import kotlinx.coroutines.flow.Flow

interface RoomsRepository : Syncable {

    fun getPagingRooms(): Flow<PagingData<Room>>

    fun createRoom(
        collocutorId: Long,
        name: String? = null,
        image: String? = null
    ): Flow<Room>

    fun findRoom(
        collocutorId: Long,
    ): Flow<Room>

}