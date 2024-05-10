package com.tynkovski.apps.messenger.core.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessagesPagingResponse(
    @SerialName("count") val count: Long,
    @SerialName("messages") val messages: List<MessageResponse>
)