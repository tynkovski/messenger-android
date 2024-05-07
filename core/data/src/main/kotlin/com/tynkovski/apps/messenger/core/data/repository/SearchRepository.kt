package com.tynkovski.apps.messenger.core.data.repository

import com.tynkovski.apps.messenger.core.model.Result
import com.tynkovski.apps.messenger.core.model.data.User
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun findUserByLogin(login: String): Flow<Result<User>>
}