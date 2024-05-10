package com.tynkovski.apps.messenger.core.network.impl

import androidx.tracing.trace
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.tynkovski.apps.messenger.core.network.BuildConfig
import com.tynkovski.apps.messenger.core.network.Dispatcher
import com.tynkovski.apps.messenger.core.network.MessengerDispatchers
import com.tynkovski.apps.messenger.core.network.RoomsDataSource
import com.tynkovski.apps.messenger.core.network.model.request.CreateRoomRequest
import com.tynkovski.apps.messenger.core.network.model.response.RoomResponse
import com.tynkovski.apps.messenger.core.network.model.response.RoomsPagingResponse
import com.tynkovski.apps.messenger.core.network.retrofit.RoomsNetworkApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Inject

private const val BASE_URL = BuildConfig.BACKEND_URL

class RoomsDataSourceImpl @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: dagger.Lazy<Call.Factory>,
) : RoomsDataSource {
    private val api: RoomsNetworkApi = trace("RoomsNetworkApi") {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .callFactory { okhttpCallFactory.get().newCall(it) }
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(RoomsNetworkApi::class.java)
    }

    override suspend fun createRoom(
        collocutorId: Long,
        name: String?,
        image: String?,
    ): RoomResponse {
        val request = CreateRoomRequest(
            name = name,
            image = image,
            users = listOf(collocutorId)
        )
        return api.createRoom(request)
    }

    override suspend fun getRoom(roomId: Long): RoomResponse {
        return api.getRoom(roomId)
    }

    override suspend fun getRoomsPaged(page: Long, pageSize: Int): RoomsPagingResponse {
        return api.getRoomsPaged(page, pageSize)
    }

}