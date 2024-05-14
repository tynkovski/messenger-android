package com.tynkovski.apps.messenger.core.network

import com.tynkovski.apps.messenger.core.network.model.response.RoomResponse
import com.tynkovski.apps.messenger.core.network.model.response.RoomsPagingResponse

interface RoomsDataSource {
    suspend fun getRoom(roomId: Long): RoomResponse

    suspend fun getRoomsPaged(
        page: Long,
        pageSize: Int
    ): RoomsPagingResponse

    suspend fun createRoom(
        collocutorId: Long,
        name: String? = null,
        image: String? = null
    ): RoomResponse

    suspend fun findRoom(
        collocutorId: Long,
    ): RoomResponse
}