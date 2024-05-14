package com.tynkovski.apps.messenger.core.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContactsResponse(
    @SerialName("contacts") val usersId: List<Long>
)