package com.tynkovski.apps.messenger.core.data.websockets.impl

import com.tynkovski.apps.messenger.core.data.util.MessageMapper
import com.tynkovski.apps.messenger.core.data.util.RoomMapper
import com.tynkovski.apps.messenger.core.data.websockets.BaseWebsocketClient
import com.tynkovski.apps.messenger.core.data.websockets.RealtimeWebsocketsClient
import com.tynkovski.apps.messenger.core.database.dao.MessagesDao
import com.tynkovski.apps.messenger.core.database.dao.RoomsDao
import com.tynkovski.apps.messenger.core.network.BuildConfig
import com.tynkovski.apps.messenger.core.network.Dispatcher
import com.tynkovski.apps.messenger.core.network.MessengerDispatchers
import com.tynkovski.apps.messenger.core.network.interceptors.RefreshTokenInterceptor
import com.tynkovski.apps.messenger.core.network.interceptors.TokenInterceptor
import com.tynkovski.apps.messenger.core.network.model.request.CreateRoomRequest
import com.tynkovski.apps.messenger.core.network.model.request.SendMessageRequest
import com.tynkovski.apps.messenger.core.network.model.response.MessageResponse
import com.tynkovski.apps.messenger.core.network.model.response.RoomResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

private const val WEBSOCKET_URL = BuildConfig.WEBSOCKET_URL
private const val SEND_MESSAGE = "send_message"
private const val EDIT_MESSAGE = "edit_message"
private const val DELETE_MESSAGE = "delete_message"
private const val READ_MESSAGE = "read_message"
private const val CREATE_ROOM = "create_room"
private const val UPDATE_ROOM = "update_room"
private const val RENAME_ROOM = "rename_room"
private const val DELETE_ROOM = "delete_room"
private const val INVITE_USER_TO_ROOM = "invite_user_to_room"
private const val KICK_USER_FROM_ROOM = "kick_user_from_room"
private const val JOIN_TO_ROOM = "join_to_room"
private const val QUIT_FROM_ROOM = "quit_from_room"
private const val MAKE_MODERATOR = "make_moderator"

@Singleton
class RealtimeChatWebsocketsClientImpl @Inject constructor(
    private val loggingInterceptor: HttpLoggingInterceptor,
    private val tokenInterceptor: TokenInterceptor,
    private val refreshInterceptor: RefreshTokenInterceptor,
    private val messagesDao: MessagesDao,
    private val roomsDao: RoomsDao,
    @Dispatcher(MessengerDispatchers.IO) private val dispatcher: CoroutineDispatcher,
) : RealtimeWebsocketsClient, BaseWebsocketClient(dispatcher) {
    override val isConnected = mIsWorking.asStateFlow()

    override fun start() {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(tokenInterceptor)
            .addInterceptor(refreshInterceptor)
            .readTimeout(0, TimeUnit.MILLISECONDS)
            .build()

        val request = Request.Builder()
            .url(WEBSOCKET_URL)
            .build()

        webSocketClient = okHttpClient.newWebSocket(request, listener)

        okHttpClient.dispatcher.executorService.shutdown()
    }

    override fun stop() {
        webSocketClient.close(1000, "Don't need a connection anymore")
    }

    override suspend fun sendMessage(roomId: Long, text: String): Boolean {
        val request = SendMessageRequest(roomId, text)
        return sendFrame(SEND_MESSAGE, request)
    }

    override suspend fun createRoom(collocutorId: Long, name: String?, image: String?): Boolean {
        val request = CreateRoomRequest(name = name, image = image, users = listOf(collocutorId))
        return sendFrame(CREATE_ROOM, request)
    }

    override suspend fun processServerResponse(event: String, json: String) {
        when (event) {
            CREATE_ROOM -> {
                val response = Json.decodeFromString<RoomResponse>(json)
                val room = RoomMapper.remoteToEntry(response)
                val entity = RoomMapper.entryToLocal(room)
                roomsDao.insert(entity)
            }

            RENAME_ROOM -> {
                val response = Json.decodeFromString<RoomResponse>(json)
                val room = RoomMapper.remoteToEntry(response)
                val entity = RoomMapper.entryToLocal(room)
                roomsDao.upsert(entity)
            }

            UPDATE_ROOM -> {
                val response = Json.decodeFromString<RoomResponse>(json)
                val room = RoomMapper.remoteToEntry(response)
                val entity = RoomMapper.entryToLocal(room)
                roomsDao.upsert(entity)
            }

            DELETE_ROOM -> {
                val response = Json.decodeFromString<RoomResponse>(json)
                val room = RoomMapper.remoteToEntry(response)
                val entity = RoomMapper.entryToLocal(room)
                roomsDao.upsert(entity)
            }

            JOIN_TO_ROOM -> {
                val response = Json.decodeFromString<RoomResponse>(json)
                val room = RoomMapper.remoteToEntry(response)
                val entity = RoomMapper.entryToLocal(room)
                roomsDao.upsert(entity)
            }

            QUIT_FROM_ROOM -> {
                val response = Json.decodeFromString<RoomResponse>(json)
                val room = RoomMapper.remoteToEntry(response)
                val entity = RoomMapper.entryToLocal(room)
                roomsDao.upsert(entity)
            }

            INVITE_USER_TO_ROOM -> {
                val response = Json.decodeFromString<RoomResponse>(json)
                val room = RoomMapper.remoteToEntry(response)
                val entity = RoomMapper.entryToLocal(room)
                roomsDao.upsert(entity)
            }

            KICK_USER_FROM_ROOM -> {
                val response = Json.decodeFromString<RoomResponse>(json)
                val room = RoomMapper.remoteToEntry(response)
                val entity = RoomMapper.entryToLocal(room)
                roomsDao.upsert(entity)
            }

            MAKE_MODERATOR -> {
                val response = Json.decodeFromString<RoomResponse>(json)
                val room = RoomMapper.remoteToEntry(response)
                val entity = RoomMapper.entryToLocal(room)
                roomsDao.upsert(entity)
            }

            SEND_MESSAGE -> {
                val response = Json.decodeFromString<MessageResponse>(json)
                val room = MessageMapper.remoteToEntry(response)
                val entity = MessageMapper.entryToLocal(room)
                messagesDao.insert(entity)
            }

            EDIT_MESSAGE -> {
                val response = Json.decodeFromString<MessageResponse>(json)
                val room = MessageMapper.remoteToEntry(response)
                val entity = MessageMapper.entryToLocal(room)
                messagesDao.upsert(entity)
            }

            DELETE_MESSAGE -> {
                val response = Json.decodeFromString<MessageResponse>(json)
                val room = MessageMapper.remoteToEntry(response)
                val entity = MessageMapper.entryToLocal(room)
                messagesDao.upsert(entity)
            }

            READ_MESSAGE -> {
                val response = Json.decodeFromString<MessageResponse>(json)
                val room = MessageMapper.remoteToEntry(response)
                val entity = MessageMapper.entryToLocal(room)
                messagesDao.upsert(entity)
            }

            else -> error("Unknown response")
        }
    }
}