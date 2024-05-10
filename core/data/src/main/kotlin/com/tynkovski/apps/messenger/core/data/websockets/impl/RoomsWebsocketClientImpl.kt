package com.tynkovski.apps.messenger.core.data.websockets.impl

import android.util.Log
import com.tynkovski.apps.messenger.core.data.util.RoomMapper
import com.tynkovski.apps.messenger.core.data.websockets.RoomsWebsocketClient
import com.tynkovski.apps.messenger.core.database.dao.RoomsDao
import com.tynkovski.apps.messenger.core.datastore.TokenHolder
import com.tynkovski.apps.messenger.core.network.BuildConfig
import com.tynkovski.apps.messenger.core.network.Dispatcher
import com.tynkovski.apps.messenger.core.network.MessengerDispatchers
import com.tynkovski.apps.messenger.core.network.interceptors.RefreshTokenInterceptor
import com.tynkovski.apps.messenger.core.network.interceptors.TokenInterceptor
import com.tynkovski.apps.messenger.core.network.model.request.CreateRoomRequest
import com.tynkovski.apps.messenger.core.network.model.response.RoomResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val ROOM_WS_URL = "ws://192.168.0.109:8080/room"
private const val CREATE_ROOM = "create_room"
private const val RENAME_ROOM = "rename_room"
private const val DELETE_ROOM = "delete_room"
private const val INVITE_USER_TO_ROOM = "invite_user_to_room"
private const val KICK_USER_FROM_ROOM = "kick_user_from_room"
private const val JOINED_TO_ROOM = "joined_to_room"
private const val QUIT_FROM_ROOM = "quit_from_room"
private const val MAKE_MODERATOR = "make_moderator"

class RoomsWebsocketClientImpl @Inject constructor(
    private val dao: RoomsDao,
    private val tokenInterceptor: TokenInterceptor,
    private val refreshInterceptor: RefreshTokenInterceptor,
    @Dispatcher(MessengerDispatchers.IO) private val dispatcher: CoroutineDispatcher,
) : RoomsWebsocketClient {
    private lateinit var webSocketClient: WebSocket

    override fun start() {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
        }

        val client = OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(tokenInterceptor)
            .addInterceptor(refreshInterceptor)
            .readTimeout(0, TimeUnit.MILLISECONDS)
            .build()

        val request = Request.Builder()
            .url(ROOM_WS_URL)
            ///.apply { accessToken?.let { header("Authorization", "Bearer $it") } }
            .build()

        webSocketClient = client.newWebSocket(request, listener)

        client.dispatcher.executorService.shutdown()
    }

    override fun stop() {
        webSocketClient.close(1000, "Don't need connection anymore")
    }

    private val listener = object : WebSocketListener() {
        private fun processServerResponse(event: String, json: String) {
            val response = Json.decodeFromString<RoomResponse>(json)
            val room = RoomMapper.remoteToEntry(response)
            val entity = RoomMapper.entryToLocal(room)
            CoroutineScope(dispatcher).launch {
                when (event) {
                    CREATE_ROOM -> dao.insert(entity)
                    RENAME_ROOM -> dao.upsert(entity)
                    DELETE_ROOM -> dao.upsert(entity) // soft delete
                    JOINED_TO_ROOM -> dao.upsert(entity)
                    QUIT_FROM_ROOM -> dao.upsert(entity)
                    INVITE_USER_TO_ROOM -> dao.upsert(entity)
                    KICK_USER_FROM_ROOM -> dao.upsert(entity)
                    MAKE_MODERATOR -> dao.upsert(entity)
                    else -> error("Unknown response")
                }
            }
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            val (event, json) = text
                .split("#", limit = 2)
                .map(String::trim)
            processServerResponse(event, json)
        }

        override fun onOpen(webSocket: WebSocket, response: Response) {
            Log.d("RoomsWebsocket", "onOpen")
        }
    }

    override fun createRoom(collocutorId: Long, name: String?, image: String?) = flow {
        val room = CreateRoomRequest(name = name, image = image, users = listOf(collocutorId))
        val json = Json.encodeToJsonElement(room).toString()
        emit(webSocketClient.send(json))
    }
}