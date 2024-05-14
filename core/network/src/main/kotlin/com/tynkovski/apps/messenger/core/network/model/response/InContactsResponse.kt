package com.tynkovski.apps.messenger.core.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class InContactsResponse(
    val inContacts: Boolean
)