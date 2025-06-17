package com.example.clientaidant.data.api


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.clientaidant.network.EndUserInfo
import com.example.clientaidant.network.HelperWebSocketClient
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

data class HelperConnectionState(
    val isConnected: Boolean = false,
    val activeUsers: List<EndUserInfo> = emptyList()
)



class HelperWebSocketService {
    private val webSocketClient = HelperWebSocketClient("ws://172.20.10.3:3002/ws")

    private val _connectionState = MutableLiveData(HelperConnectionState())
    val connectionState: LiveData<HelperConnectionState> = _connectionState

    private val _locationUpdates = MutableLiveData<EndUserInfo>()
    val locationUpdates: LiveData<EndUserInfo> = _locationUpdates

    private val _userConnections = MutableLiveData<EndUserInfo>()
    val userConnections: LiveData<EndUserInfo> = _userConnections

    private val _userDisconnections = MutableLiveData<Int>()
    val userDisconnections: LiveData<Int> = _userDisconnections

    init {
        setupWebSocketHandlers()
    }

    private fun setupWebSocketHandlers() {
        webSocketClient.setConnectionStatusHandler { isConnected ->
            _connectionState.postValue(_connectionState.value?.copy(isConnected = isConnected))
        }

        webSocketClient.setActiveUsersHandler { users ->
            _connectionState.postValue(_connectionState.value?.copy(activeUsers = users))
        }

        webSocketClient.setLocationUpdateHandler { user ->
            _locationUpdates.postValue(user)
            // Mettre à jour la liste des utilisateurs actifs avec la nouvelle position
            val currentUsers = _connectionState.value?.activeUsers?.toMutableList() ?: mutableListOf()
            val userIndex = currentUsers.indexOfFirst { it.id == user.id }
            if (userIndex != -1) {
                Log.d("ANCIEN USER",currentUsers[userIndex].toString())
                Log.d("NEW USER",user.toString())

                currentUsers[userIndex] = user
                _connectionState.postValue(_connectionState.value?.copy(activeUsers = currentUsers))
            }
        }

        webSocketClient.setUserConnectedHandler { user ->
            _userConnections.postValue(user)
            // Ajouter l'utilisateur à la liste des utilisateurs actifs
            val currentUsers = _connectionState.value?.activeUsers?.toMutableList() ?: mutableListOf()
            if (currentUsers.none { it.id == user.id }) {
                currentUsers.add(user)
                _connectionState.postValue(_connectionState.value?.copy(activeUsers = currentUsers))
            }
        }

        webSocketClient.setUserDisconnectedHandler { userId ->
            _userDisconnections.postValue(userId)
            // Retirer l'utilisateur de la liste des utilisateurs actifs
            val currentUsers = _connectionState.value?.activeUsers?.toMutableList() ?: mutableListOf()
            currentUsers.removeAll { it.id == userId }
            _connectionState.postValue(_connectionState.value?.copy(activeUsers = currentUsers))
        }
    }

    fun connect(helperId: Int) {
        webSocketClient.connect(helperId)
    }

    fun disconnect() {
        webSocketClient.disconnect()
    }

    fun cleanup() {
        disconnect()
    }

    fun setServerUrl(url: String) {
        webSocketClient.setUrl(url)
    }
}


