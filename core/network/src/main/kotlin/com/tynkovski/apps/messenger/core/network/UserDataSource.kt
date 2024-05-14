package com.tynkovski.apps.messenger.core.network

import com.tynkovski.apps.messenger.core.network.model.response.UserResponse

interface UserDataSource {
    suspend fun getUser(): UserResponse

    suspend fun getUser(id: Long): UserResponse

    suspend fun editUser(name: String? = null, image: String? = null): UserResponse
}