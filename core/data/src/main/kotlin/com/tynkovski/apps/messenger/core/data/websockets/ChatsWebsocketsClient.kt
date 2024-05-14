package com.tynkovski.apps.messenger.core.data.websockets

interface ChatsWebsocketsClient {
    fun start()

    fun stop()

    fun sendMessage(roomId: Long, text: String): Boolean
}