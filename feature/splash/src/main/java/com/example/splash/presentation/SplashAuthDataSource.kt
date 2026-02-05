package com.example.splash.presentation

import com.example.models.AuthDataSource
import com.example.splash.data.SplashRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SplashAuthDataSource @Inject constructor(
    private val splashRepository: SplashRepository
) : AuthDataSource {

    override suspend fun hasValidCredentials(): Boolean {
        return splashRepository.hasSavedCredentials()
    }
}