package com.tynkovski.apps.messenger.core.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoomsPagingResponse(
    @SerialName("page") val page: Long,
    @SerialName("total_count") val count: Long,
    @SerialName("rooms") val rooms: List<RoomResponse>
)
