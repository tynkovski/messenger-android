package com.tynkovski.apps.messenger.core.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SimpleMessageResponse(
    @SerialName("message") val message: String
)
