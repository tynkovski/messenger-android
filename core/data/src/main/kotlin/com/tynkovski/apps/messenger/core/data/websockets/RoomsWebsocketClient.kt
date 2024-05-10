package com.tynkovski.apps.messenger.core.data.websockets

import kotlinx.coroutines.flow.Flow

interface RoomsWebsocketClient {
    fun start()

    fun stop()

    fun createRoom(collocutorId: Long, name: String? = null, image: String? = null): Flow<Boolean>
}