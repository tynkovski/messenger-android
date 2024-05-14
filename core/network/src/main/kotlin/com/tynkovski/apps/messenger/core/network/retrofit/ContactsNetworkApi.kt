package com.tynkovski.apps.messenger.core.network.retrofit

import com.tynkovski.apps.messenger.core.network.model.response.UsersResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

private const val CONTACTS_URL = "/contact"

interface ContactsNetworkApi {
    @GET(CONTACTS_URL)
    suspend fun getContacts(): UsersResponse

    @POST(CONTACTS_URL)
    suspend fun addContact(
        @Query("id", encoded = true) userId: Long
    ): UsersResponse

    @DELETE(CONTACTS_URL)
    suspend fun removeContact(
        @Query("id", encoded = true) userId: Long
    ): UsersResponse
}