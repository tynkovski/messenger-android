package com.tynkovski.apps.messenger.core.data.websockets

import kotlinx.coroutines.flow.StateFlow

interface ChatsWebsocketsClient {
    val isConnected: StateFlow<Boolean>

    fun start()

    fun stop()

    fun sendMessage(roomId: Long, text: String): Boolean
}