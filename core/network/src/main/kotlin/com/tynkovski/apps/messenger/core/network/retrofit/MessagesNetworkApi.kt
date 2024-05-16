package com.tynkovski.apps.messenger.core.network.retrofit

import com.tynkovski.apps.messenger.core.network.model.response.MessageResponse
import com.tynkovski.apps.messenger.core.network.model.response.MessagesPagingResponse
import com.tynkovski.apps.messenger.core.network.model.response.UnreadResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val MESSAGES_API = "/message"

interface MessagesNetworkApi {

    @GET("$MESSAGES_API/unread/{id}")
    suspend fun getUnreadMessagesCount(
        @Path(value = "id", encoded = true) roomId: Long
    ): UnreadResponse

    @GET("$MESSAGES_API/{id}")
    suspend fun getMessage(
        @Path(value = "id", encoded = true) messageId: Long
    ): MessageResponse

    @GET("$MESSAGES_API/paged/{roomId}")
    suspend fun getMessagesPaged(
        @Path(value = "roomId", encoded = true) roomId: Long,
        @Query("page") page: Long,
        @Query("pageSize") pageSize: Int
    ): MessagesPagingResponse
}