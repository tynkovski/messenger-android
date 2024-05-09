package com.tynkovski.apps.messenger.core.data.repository

import com.tynkovski.apps.messenger.core.data.Syncable
import com.tynkovski.apps.messenger.core.model.Result
import com.tynkovski.apps.messenger.core.model.data.User
import kotlinx.coroutines.flow.Flow

interface ContactsRepository : Syncable {
    fun getContacts(): Flow<List<User>>

    fun addContact(userId: Long): Flow<Unit>

    fun removeContact(userId: Long): Flow<Unit>
}