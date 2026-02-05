package com.example.splash.di

import com.example.models.AuthStateManager
import com.example.splash.presentation.SplashViewModel
import javax.inject.Inject

class SplashViewModelFactory @Inject constructor(
    private val authStateManager : AuthStateManager
) {
    fun create() : SplashViewModel = SplashViewModel(authStateManager)
}