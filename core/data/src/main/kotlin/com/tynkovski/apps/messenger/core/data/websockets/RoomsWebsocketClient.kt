package com.tynkovski.apps.messenger.core.data.websockets

import kotlinx.coroutines.flow.Flow

interface RoomsWebsocketClient {
    val isConnected: Flow<Boolean>
    fun start()
    fun stop()
    fun createRoom(collocutorId: Long, name: String? = null, image: String? = null): Boolean
}