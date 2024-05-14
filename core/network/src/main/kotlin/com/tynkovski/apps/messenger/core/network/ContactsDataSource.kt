package com.tynkovski.apps.messenger.core.network

import com.tynkovski.apps.messenger.core.network.model.response.UsersResponse

interface ContactsDataSource {
    suspend fun getContacts(): UsersResponse

    suspend fun addContact(userId: Long): UsersResponse

    suspend fun removeContact(userId: Long): UsersResponse
}