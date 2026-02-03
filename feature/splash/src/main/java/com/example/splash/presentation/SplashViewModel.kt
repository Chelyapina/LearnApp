package com.example.splash.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.splash.data.SplashRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val repository : SplashRepository
) : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState : StateFlow<AuthState> = _authState

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus() {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val hasCredentials = repository.hasSavedCredentials()
                _authState.value = if (hasCredentials) {
                    AuthState.Authenticated
                } else {
                    AuthState.Unauthenticated
                }
            } catch (e : Exception) {
                _authState.value = AuthState.Error(
                    message = e.message ?: "Ошибка проверки авторизации"
                )
            }
        }
    }

    sealed class AuthState {
        object Loading : AuthState()
        object Authenticated : AuthState()
        object Unauthenticated : AuthState()
        data class Error(val message : String) : AuthState()
    }
}