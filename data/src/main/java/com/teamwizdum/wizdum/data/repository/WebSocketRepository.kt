package com.teamwizdum.wizdum.data.repository

import com.teamwizdum.wizdum.data.model.ChatMessage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WebSocketRepository @Inject constructor() {

    private val WEB_SOCKET_URL = "ws://211.188.49.238:8081/ask"

    private val client = OkHttpClient.Builder()
        .pingInterval(10, TimeUnit.SECONDS)
        .build()

    private val request = Request.Builder()
        .url(WEB_SOCKET_URL)
        .build()

    private var webSocket: WebSocket? = null
    private val messageChannel = Channel<ChatMessage>(Channel.BUFFERED)

    val jsonFormatter = Json { encodeDefaults = true }

    fun connect() {
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                Timber.tag("CHAT").d("socket open!")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                Timber.tag("CHAT").d("socket message: $text")

                try {
                    val message = Json.decodeFromString<ChatMessage>(text)
                    messageChannel.trySend(message).isSuccess
                } catch (e: Exception) {
                    Timber.tag("CHAT").d("Parsing error: $e")
                }
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                Timber.tag("CHAT").d("socket closed!")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                Timber.tag("CHAT").d("socket failed! : $response")
            }
        })
    }

    fun sendMessage(messageData: ChatMessage) {
        val jsonMessage = jsonFormatter.encodeToString(messageData)
        webSocket?.send(jsonMessage)
        Timber.tag("CHAT").d("메세지 전송 : $jsonMessage")
    }

    fun close() {
        webSocket?.close(1000, "Client closed connection")
        webSocket = null
    }

    fun observeMessage(): Flow<ChatMessage> = messageChannel.receiveAsFlow()
}