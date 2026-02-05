package com.example.splash.presentation

import androidx.lifecycle.ViewModel
import com.example.models.AuthState
import com.example.models.AuthStateManager
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    authStateManager : AuthStateManager
) : ViewModel() {
    val authState : StateFlow<AuthState> = authStateManager.authState

    init {
        authStateManager.checkAuthStatus()
    }
}