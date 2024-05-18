package com.tynkovski.apps.messenger.core.network.impl

import androidx.tracing.trace
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.tynkovski.apps.messenger.core.network.BuildConfig
import com.tynkovski.apps.messenger.core.network.ContactsDataSource
import com.tynkovski.apps.messenger.core.network.model.response.ContactsResponse
import com.tynkovski.apps.messenger.core.network.model.response.SimpleMessageResponse
import com.tynkovski.apps.messenger.core.network.model.response.UsersResponse
import com.tynkovski.apps.messenger.core.network.retrofit.ContactsNetworkApi
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Inject

private const val BASE_URL = BuildConfig.BACKEND_URL

class ContactsDataSourceImpl @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: dagger.Lazy<Call.Factory>,
) : ContactsDataSource {
    private val api: ContactsNetworkApi = trace("ContactsNetworkApi") {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .callFactory { okhttpCallFactory.get().newCall(it) }
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create(ContactsNetworkApi::class.java)
    }

    override suspend fun getContacts(): UsersResponse {
        return api.getContacts()
    }

    override suspend fun addContact(userId: Long): UsersResponse {
        return api.addContact(userId)
    }

    override suspend fun removeContact(userId: Long): UsersResponse {
        return api.removeContact(userId)
    }
}