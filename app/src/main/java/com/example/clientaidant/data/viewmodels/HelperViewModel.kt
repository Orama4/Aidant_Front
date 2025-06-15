package com.example.clientaidant.data.viewmodels

import com.example.clientaidant.data.api.ActiveEndUser
import com.example.clientaidant.repositories.HelperRepository

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HelperViewModel(
    private val helperRepository: HelperRepository,
    private val context: Context
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    private val _activeEndUsers = MutableStateFlow<List<ActiveEndUser>>(emptyList())
    val activeEndUsers: StateFlow<List<ActiveEndUser>> get() = _activeEndUsers

    fun fetchActiveEndUsers(helperUserId: Int) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val result = helperRepository.getActiveEndUsers(helperUserId)

            result
                .onSuccess { response ->
                    Log.d("HelperVM", "Raw response: $response")
                    Log.d("HelperVM", "Users count: ${response.users.size}")
                    response.users.forEachIndexed { index, user ->
                        Log.d("HelperVM", "User $index: $user")
                    }
                    _activeEndUsers.value = response.users
                }
                .onFailure { e ->
                    _error.value = e.message
                    Log.e("HelperVM", "Failed to fetch end users", e)
                }

            _loading.value = false
        }
    }
}
