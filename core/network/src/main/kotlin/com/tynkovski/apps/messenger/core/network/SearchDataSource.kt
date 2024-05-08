package com.tynkovski.apps.messenger.core.network

import com.tynkovski.apps.messenger.core.network.model.UserResponse

interface SearchDataSource {
    suspend fun getUser(login: String): UserResponse
}