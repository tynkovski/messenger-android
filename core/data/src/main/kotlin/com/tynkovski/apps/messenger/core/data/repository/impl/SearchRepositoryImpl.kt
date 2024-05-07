package com.tynkovski.apps.messenger.core.data.repository.impl

import com.tynkovski.apps.messenger.core.data.repository.SearchRepository
import com.tynkovski.apps.messenger.core.model.Result
import com.tynkovski.apps.messenger.core.model.data.User
import kotlinx.coroutines.flow.Flow

class SearchRepositoryImpl: SearchRepository {
    override fun findUserByLogin(login: String): Flow<Result<User>> {
        TODO("Not yet implemented")
    }
}