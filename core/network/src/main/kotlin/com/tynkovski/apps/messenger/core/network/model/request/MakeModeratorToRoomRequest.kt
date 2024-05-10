package com.tynkovski.apps.messenger.core.network.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MakeModeratorToRoomRequest(
    @SerialName("roomId") val roomId: Long,
    @SerialName("userId") val userId: Long
)
