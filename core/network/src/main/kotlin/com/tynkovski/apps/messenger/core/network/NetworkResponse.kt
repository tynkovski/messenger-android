package com.tynkovski.apps.messenger.core.network

import kotlinx.serialization.Serializable

@Serializable
data class NetworkResponse<T>(val data: T)