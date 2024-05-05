package com.tynkovski.apps.messenger.core.network.model

import com.tynkovski.apps.messenger.core.model.data.Token
import kotlinx.serialization.Serializable

/**
 * Network representation of [Token]
 */
@Serializable
data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)