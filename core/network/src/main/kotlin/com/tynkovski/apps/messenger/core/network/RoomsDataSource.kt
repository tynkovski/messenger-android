package com.tynkovski.apps.messenger.core.network

import com.tynkovski.apps.messenger.core.network.model.RoomResponse

interface RoomsDataSource {
    fun createRoom(collocutorId: Long): RoomResponse
}