package com.tynkovski.apps.messenger.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class AccessResponse(
    val accessToken: String
)