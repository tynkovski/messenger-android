package com.tynkovski.apps.messenger.core.data.websockets

interface RoomsWebsocketClient {
    fun start()
    fun stop()
    fun createRoom(collocutorId: Long, name: String? = null, image: String? = null): Boolean
}