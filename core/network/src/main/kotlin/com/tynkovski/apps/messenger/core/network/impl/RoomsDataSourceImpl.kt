package com.tynkovski.apps.messenger.core.network.impl

import androidx.tracing.trace
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.tynkovski.apps.messenger.core.network.BuildConfig
import com.tynkovski.apps.messenger.core.network.RoomsDataSource
import com.tynkovski.apps.messenger.core.network.model.RoomResponse
import com.tynkovski.apps.messenger.core.network.retrofit.RoomsNetworkApi
import com.tynkovski.apps.messenger.core.network.websocket.RoomsWebsocketListener
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val BASE_URL = BuildConfig.BACKEND_URL
private const val BASE_WS_URL = "ws://$BASE_URL/room"

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

    private val websocketClient: WebSocket = trace("WebsocketClient") {
        val client = OkHttpClient
            .Builder()
            .readTimeout(0, TimeUnit.MILLISECONDS)
            .build()

        val request = Request.Builder()
            .url(BASE_WS_URL)
            .build()

        client.newWebSocket(request, roomsWebsocketListener)
    }

    override fun createRoom(collocutorId: Long): RoomResponse {
        TODO("Not yet implemented")
    }

    val flow = roomsWebsocketListener.flow
}