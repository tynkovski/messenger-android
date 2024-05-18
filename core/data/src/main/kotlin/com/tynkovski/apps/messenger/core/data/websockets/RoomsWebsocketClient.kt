package com.tynkovski.apps.messenger.core.data.websockets

import kotlinx.coroutines.flow.StateFlow

interface RoomsWebsocketClient {
    val isConnected: StateFlow<Boolean>
    fun start()
    fun stop()
    fun createRoom(collocutorId: Long, name: String? = null, image: String? = null): Boolean
}