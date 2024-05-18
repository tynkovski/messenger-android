package com.tynkovski.apps.messenger.core.data.websockets

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

abstract class BaseWebsocketClient(
    private val dispatcher: CoroutineDispatcher
) {
    protected val mIsWorking = MutableStateFlow(false)

    protected lateinit var webSocketClient: WebSocket

    protected abstract suspend fun processServerResponse(event: String, json: String)

    protected inline fun <reified T> sendFrame(action: String, request: T): Boolean {
        val json = Json.encodeToJsonElement(request).toString()
        val frame = "$action#$json"
        return webSocketClient.send(frame)
    }

    protected val listener = object : WebSocketListener() {
        override fun onMessage(webSocket: WebSocket, text: String) {
            val (event, json) = text
                .split("#", limit = 2)
                .map(String::trim)
            CoroutineScope(dispatcher).launch {
                processServerResponse(event, json)
            }
        }

        override fun onOpen(webSocket: WebSocket, response: Response) {
            mIsWorking.value = true
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            mIsWorking.value = false
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            mIsWorking.value = false
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            mIsWorking.value = false
        }
    }
}