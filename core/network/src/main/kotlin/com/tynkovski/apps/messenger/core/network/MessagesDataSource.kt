package com.tynkovski.apps.messenger.core.network

import com.tynkovski.apps.messenger.core.network.model.response.MessageResponse
import com.tynkovski.apps.messenger.core.network.model.response.MessagesPagingResponse
import com.tynkovski.apps.messenger.core.network.model.response.UnreadResponse

interface MessagesDataSource {

    suspend fun getUnreadMessagesCount(roomId: Long): UnreadResponse

    suspend fun getMessage(messageId: Long): MessageResponse

    suspend fun getMessagesPaged(
        roomId: Long,
        page: Long,
        pageSize: Int
    ): MessagesPagingResponse

}