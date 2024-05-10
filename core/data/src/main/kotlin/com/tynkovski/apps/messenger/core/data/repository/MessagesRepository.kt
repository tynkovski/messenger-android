package com.tynkovski.apps.messenger.core.data.repository

import com.tynkovski.apps.messenger.core.data.Syncable
import kotlinx.coroutines.flow.Flow

interface MessagesRepository : Syncable {
    fun getUnreadMessagesCount(roomId: Long): Flow<Int>
}