package com.tynkovski.apps.messenger.core.data.repository

import com.tynkovski.apps.messenger.core.model.data.User
import kotlinx.coroutines.flow.Flow

interface ContactsRepository {
    fun getContacts(): Flow<List<User>>

    fun addContact(userId: Long): Flow<List<User>>

    fun removeContact(userId: Long): Flow<List<User>>
}