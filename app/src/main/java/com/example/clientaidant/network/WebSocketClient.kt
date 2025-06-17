package com.example.clientaidant.network

// HelperWebSocketClient.kt

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.json.JSONArray
import java.util.concurrent.TimeUnit
import okhttp3.*

data class EndUserInfo(
    val id: Int,
    val userId: Int,
    val username: String,
    val lastPosition: Position? = null,
    val heading : Float,
    val status: String,
    val isOnline: Boolean = false
)

data class Position(
    val lat: Double,
    val lng: Double,
    val timestamp: String
)

class HelperWebSocketClient(private var url: String) {
    private val TAG = "HelperWebSocketClient"
    private var webSocket: WebSocket? = null
    private val client = OkHttpClient.Builder()
        .readTimeout(0, TimeUnit.MILLISECONDS)
        .build()

    private var isConnected = false
    private var reconnectJob: Job? = null

    // Callbacks
    private var onActiveUsersReceived: ((List<EndUserInfo>) -> Unit)? = null
    private var onLocationUpdateReceived: ((EndUserInfo) -> Unit)? = null
    private var onUserConnected: ((EndUserInfo) -> Unit)? = null
    private var onUserDisconnected: ((Int) -> Unit)? = null
    private var onConnectionStatusChanged: ((Boolean) -> Unit)? = null

    fun connect(helperId: Int) {
        val helperUrl = "$url?role=helper&userId=$helperId&helperId=1"
        val request = Request.Builder()
            .url(helperUrl)
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.d(TAG, "Helper WebSocket connection established")
                isConnected = true
                onConnectionStatusChanged?.invoke(true)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                try {
                    val json = JSONObject(text)
                    val type = json.getString("type")

                    when (type) {
                        "active_users" -> {
                            val usersArray = json.getJSONArray("users")
                            val usersList = mutableListOf<EndUserInfo>()

                            for (i in 0 until usersArray.length()) {
                                val userJson = usersArray.getJSONObject(i)
                                val user = parseEndUserInfo(userJson)
                                usersList.add(user)
                            }

                            CoroutineScope(Dispatchers.Main).launch {
                                onActiveUsersReceived?.invoke(usersList)
                                Log.d(TAG, "Received active users: ${usersList.size}")
                            }
                        }

                        "location_update" -> {
                            val userJson = json.getJSONObject("user")
                            val user = parseEndUserInfo(userJson)

                            CoroutineScope(Dispatchers.Main).launch {
                                onLocationUpdateReceived?.invoke(user)
                                Log.d(TAG, "Location update for user ${user.id}: ${user.lastPosition}")
                            }
                        }

                        "user_connected" -> {
                            val userJson = json.getJSONObject("user")
                            val user = parseEndUserInfo(userJson)

                            CoroutineScope(Dispatchers.Main).launch {
                                onUserConnected?.invoke(user)
                                Log.d(TAG, "User connected: ${user.username}")
                            }
                        }

                        "user_disconnected" -> {
                            val userId = json.getInt("userId")

                            CoroutineScope(Dispatchers.Main).launch {
                                onUserDisconnected?.invoke(userId)
                                Log.d(TAG, "User disconnected: $userId")
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error parsing message: $text", e)
                }
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                Log.d(TAG, "Helper WebSocket closing: $code - $reason")
                webSocket.close(1000, null)
                isConnected = false
                onConnectionStatusChanged?.invoke(false)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.e(TAG, "Helper WebSocket failure", t)
                isConnected = false
                onConnectionStatusChanged?.invoke(false)
                reconnect()
            }
        })
    }

    private fun parseEndUserInfo(userJson: JSONObject): EndUserInfo {
        var gg = 0.0f ;
        if(userJson.has("heading")){
             gg = userJson.getDouble("heading").toFloat()
             Log.d("GEGEGEGEG",gg.toString())

         }
        val position = if (userJson.has("position")) {
            val posJson = userJson.getJSONObject("position")
            Position(
                lat = posJson.getDouble("lat"),
                lng = posJson.getDouble("lng"),
                timestamp = posJson.getString("timestamp")
            )
        } else if (userJson.has("lastPosition") && !userJson.isNull("lastPosition")) {
            val posJson = userJson.getJSONObject("lastPosition")
            Position(
                lat = posJson.getDouble("lat"),
                lng = posJson.getDouble("lng"),
                timestamp = posJson.getString("timestamp")
            )
        } else null

        return EndUserInfo(
            id = userJson.getInt("id"),
            userId = userJson.getInt("userId"),
            username = userJson.getString("username"),
            lastPosition = position,
             heading = gg ,
            status = userJson.optString("status", "offline"),
            isOnline = userJson.optBoolean("isOnline", false)
        )
    }

    private fun reconnect() {
        if (reconnectJob?.isActive == true) return

        reconnectJob = CoroutineScope(Dispatchers.IO).launch {
            kotlinx.coroutines.delay(3000)
            // Note: Need to store helperId for reconnection
            Log.d(TAG, "Attempting to reconnect...")
        }
    }

    fun disconnect() {
        webSocket?.close(1000, "Helper requested disconnect")
        webSocket = null
        reconnectJob?.cancel()
        isConnected = false
        onConnectionStatusChanged?.invoke(false)
    }

    // Setters for callbacks
    fun setActiveUsersHandler(handler: (List<EndUserInfo>) -> Unit) {
        onActiveUsersReceived = handler
    }

    fun setLocationUpdateHandler(handler: (EndUserInfo) -> Unit) {
        onLocationUpdateReceived = handler
    }

    fun setUserConnectedHandler(handler: (EndUserInfo) -> Unit) {
        onUserConnected = handler
    }

    fun setUserDisconnectedHandler(handler: (Int) -> Unit) {
        onUserDisconnected = handler
    }

    fun setConnectionStatusHandler(handler: (Boolean) -> Unit) {
        onConnectionStatusChanged = handler
    }

    fun setUrl(newUrl: String) {
        url = newUrl
    }

    fun isConnected(): Boolean = isConnected
}
