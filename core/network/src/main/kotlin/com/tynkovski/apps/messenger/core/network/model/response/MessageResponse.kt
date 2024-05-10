package com.tynkovski.apps.messenger.core.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageResponse(
    @SerialName("id") val id: Long,
    @SerialName("senderId") val senderId: Long,
    @SerialName("roomId") val roomId: Long,
    @SerialName("text") val text: String,
    @SerialName("readBy") val readBy: List<Long>,
    @SerialName("editedAt") val editedAt: String?,
    @SerialName("sentAt") val sentAt: String,
    @SerialName("isDeleted") val isDeleted: Boolean,
)
