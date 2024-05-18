package com.tynkovski.apps.messenger.core.data.repository

import androidx.paging.PagingData
import com.tynkovski.apps.messenger.core.data.Syncable
import com.tynkovski.apps.messenger.core.model.data.Message
import com.tynkovski.apps.messenger.core.model.data.Room
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MessagesRepository : Syncable {
    val isConnected: StateFlow<Boolean>

    fun startWebsocket()

    fun stopWebsocket()

    fun getPagingMessages(roomId: Long): Flow<PagingData<Message>>

    fun sendMessage(roomId: Long, message: String): Flow<Boolean>

}