package com.tynkovski.apps.messenger.core.data.websockets.impl

import com.tynkovski.apps.messenger.core.data.util.MessageMapper
import com.tynkovski.apps.messenger.core.data.websockets.BaseWebsocketClient
import com.tynkovski.apps.messenger.core.data.websockets.ChatsWebsocketsClient
import com.tynkovski.apps.messenger.core.database.dao.MessagesDao
import com.tynkovski.apps.messenger.core.network.Dispatcher
import com.tynkovski.apps.messenger.core.network.MessengerDispatchers
import com.tynkovski.apps.messenger.core.network.interceptors.RefreshTokenInterceptor
import com.tynkovski.apps.messenger.core.network.interceptors.TokenInterceptor
import com.tynkovski.apps.messenger.core.network.model.request.SendMessageRequest
import com.tynkovski.apps.messenger.core.network.model.response.MessageResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

private const val MESSAGES_WS_URL = "ws://192.168.0.109:8080/message"
private const val SEND_MESSAGE = "send_message"
private const val EDIT_MESSAGE = "edit_message"
private const val DELETE_MESSAGE = "delete_message"
private const val READ_MESSAGE = "read_message"

@Singleton
class ChatsWebsocketsClientImpl @Inject constructor(
    private val loggingInterceptor: HttpLoggingInterceptor,
    private val tokenInterceptor: TokenInterceptor,
    private val refreshInterceptor: RefreshTokenInterceptor,
    private val dao: MessagesDao,
    @Dispatcher(MessengerDispatchers.IO) private val dispatcher: CoroutineDispatcher,
) : ChatsWebsocketsClient, BaseWebsocketClient(dispatcher) {
    override val isConnected = mIsWorking.asStateFlow()

    override fun start() {
        val okHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(tokenInterceptor)
            .addInterceptor(refreshInterceptor)
            .readTimeout(0, TimeUnit.MILLISECONDS)
            .build()

        val request = Request
            .Builder()
            .url(MESSAGES_WS_URL)
            .build()

        webSocketClient = okHttpClient.newWebSocket(request, listener)

        okHttpClient.dispatcher.executorService.shutdown()
    }

    override fun stop() {
        webSocketClient.close(1000, "Don't need a connection anymore")
    }

    override fun sendMessage(roomId: Long, text: String): Boolean {
        val request = SendMessageRequest(roomId, text)
        return sendFrame(SEND_MESSAGE, request)
    }

    override suspend fun processServerResponse(event: String, json: String) {
        val response = Json.decodeFromString<MessageResponse>(json)
        val room = MessageMapper.remoteToEntry(response)
        val entity = MessageMapper.entryToLocal(room)
        when (event) {
            SEND_MESSAGE -> dao.insert(entity)
            EDIT_MESSAGE -> dao.upsert(entity)
            DELETE_MESSAGE -> dao.upsert(entity)
            READ_MESSAGE -> dao.upsert(entity)
            else -> error("Unknown response")
        }
    }

    init {
        start()
    }
}