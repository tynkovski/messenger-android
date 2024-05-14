package com.tynkovski.apps.messenger.core.network.impl

import androidx.tracing.trace
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.tynkovski.apps.messenger.core.network.BuildConfig
import com.tynkovski.apps.messenger.core.network.MessagesDataSource
import com.tynkovski.apps.messenger.core.network.model.response.MessageResponse
import com.tynkovski.apps.messenger.core.network.model.response.MessagesPagingResponse
import com.tynkovski.apps.messenger.core.network.model.response.UnreadResponse
import com.tynkovski.apps.messenger.core.network.retrofit.MessagesNetworkApi
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Inject

private const val BASE_URL = BuildConfig.BACKEND_URL

class MessagesDataSourceImpl @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: dagger.Lazy<Call.Factory>,
) : MessagesDataSource {
    private val api: MessagesNetworkApi = trace("UsersNetworkApi") {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .callFactory { okhttpCallFactory.get().newCall(it) }
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create(MessagesNetworkApi::class.java)
    }

    override suspend fun getUnreadMessagesCount(roomId: Long): UnreadResponse {
        return api.getUnreadMessagesCount(roomId)
    }

    override suspend fun getMessage(messageId: Long): MessageResponse {
        return api.getMessage(messageId)
    }

    override suspend fun getMessagesPaged(page: Long, pageSize: Int): MessagesPagingResponse {
        return api.getMessagesPaged(page, pageSize)
    }
}