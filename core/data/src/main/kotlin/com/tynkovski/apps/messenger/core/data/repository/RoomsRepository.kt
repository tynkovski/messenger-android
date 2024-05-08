package com.tynkovski.apps.messenger.core.data.repository

import com.tynkovski.apps.messenger.core.data.Syncable
import com.tynkovski.apps.messenger.core.model.data.Room
import kotlinx.coroutines.flow.Flow


interface RoomsRepository : Syncable {
    fun createRoom(collocutorId: Long): Flow<Room>
}