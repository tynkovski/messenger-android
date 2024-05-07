package com.tynkovski.apps.messenger.core.data.repository

import com.tynkovski.apps.messenger.core.data.Syncable
import com.tynkovski.apps.messenger.core.model.Result
import com.tynkovski.apps.messenger.core.model.data.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository : Syncable {
    fun getUser(): Flow<Result<User>>

    fun getUser(id: Long): Flow<Result<User>>

    fun editUser(name: String? = null, image: String? = null): Flow<Result<User>>
}