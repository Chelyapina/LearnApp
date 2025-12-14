package com.example.navigation.splash.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigation.splash.data.SplashRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

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

@Module
object SplashModule {
    @Provides
    @Singleton
    fun provideSplashViewModelFactory(
        repository : SplashRepository
    ) : SplashViewModelFactory {
        return SplashViewModelFactory(repository)
    }
}

class SplashViewModelFactory @Inject constructor(
    private val repository : SplashRepository
) {
    fun create() : SplashViewModel {
        return SplashViewModel(repository)
    }
}