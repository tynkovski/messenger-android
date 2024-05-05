package com.tynkovski.apps.messenger.core.network.model

import com.tynkovski.apps.messenger.core.model.data.AccessToken
import kotlinx.serialization.Serializable

@Serializable
data class AccessResponse(
    val accessToken: String
)

fun AccessResponse.asExternalModel(): AccessToken  = AccessToken(
    accessToken = accessToken
)