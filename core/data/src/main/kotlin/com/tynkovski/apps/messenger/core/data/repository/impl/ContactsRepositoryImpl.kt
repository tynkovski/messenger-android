package com.tynkovski.apps.messenger.core.data.repository.impl

import com.tynkovski.apps.messenger.core.data.Synchronizer
import com.tynkovski.apps.messenger.core.data.repository.ContactsRepository
import com.tynkovski.apps.messenger.core.model.Result
import com.tynkovski.apps.messenger.core.model.data.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class ContactsRepositoryImpl @Inject constructor(
    
) : ContactsRepository {
    override fun getContacts(): Flow<Result<List<User>>> {
        TODO("Not yet implemented")
    }

    override fun addContact(userId: Long): Flow<Result<Unit>> {
        TODO("Not yet implemented")
    }

    override fun removeContact(userId: Long): Flow<Result<Unit>> {
        TODO("Not yet implemented")
    }

    override suspend fun syncWith(synchronizer: Synchronizer) = true
}