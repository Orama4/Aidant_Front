package com.example.clientaidant.data.viewmodels
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.clientaidant.repositories.AuthRepository

class AuthViewModelFactory(private val authRepository: AuthRepository, private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(authRepository,context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
