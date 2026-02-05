package com.example.models

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthStateManager @Inject constructor(
    private val authDataSource : AuthDataSource
) {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState : StateFlow<AuthState> = _authState.asStateFlow()

    private val _shouldRefresh = MutableSharedFlow<Unit>(
        extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val shouldRefresh : SharedFlow<Unit> = _shouldRefresh.asSharedFlow()

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus() {
        scope.launch {
            _authState.value = AuthState.Loading
            try {
                val hasCredentials = authDataSource.hasValidCredentials()
                val newState = if (hasCredentials) {
                    AuthState.Authenticated
                } else {
                    AuthState.Unauthenticated
                }
                if (_authState.value != newState) {
                    _authState.value = newState
                }
                _shouldRefresh.emit(Unit)
            } catch (e : Exception) {
                _authState.value = AuthState.Error(
                    message = e.message ?: ERROR_CHECK_MESSAGE
                )
            }
        }
    }

    suspend fun notifyAuthChanged(isAuthenticated : Boolean) {
        if (isAuthenticated) {
            checkAuthStatus()
        } else {
            _authState.value = AuthState.Unauthenticated
            _shouldRefresh.emit(Unit)
        }
    }

    companion object {
        private const val ERROR_CHECK_MESSAGE = "Ошибка проверки"
    }
}