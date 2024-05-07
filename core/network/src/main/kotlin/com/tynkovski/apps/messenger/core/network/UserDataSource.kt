package com.tynkovski.apps.messenger.core.network

import com.tynkovski.apps.messenger.core.model.NetResult
import com.tynkovski.apps.messenger.core.network.model.ErrorResponse
import com.tynkovski.apps.messenger.core.network.model.UserResponse

interface UserDataSource {
    suspend fun getUser(): NetResult<UserResponse, ErrorResponse>

    suspend fun getUser(id: Long): NetResult<UserResponse, ErrorResponse>

    suspend fun editUser(name: String? = null, image: String? = null): NetResult<UserResponse, ErrorResponse>
}