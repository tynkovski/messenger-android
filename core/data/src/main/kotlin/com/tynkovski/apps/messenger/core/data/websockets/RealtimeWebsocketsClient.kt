package com.tynkovski.apps.messenger.core.data.websockets

import kotlinx.coroutines.flow.StateFlow

interface RealtimeWebsocketsClient {
    val isConnected: StateFlow<Boolean>

    fun start()

    fun stop()

    suspend fun sendMessage(roomId: Long, text: String): Boolean

    suspend fun createRoom(
        collocutorId: Long,
        name: String? = null,
        image: String? = null
    ): Boolean
}