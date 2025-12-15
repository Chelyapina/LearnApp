package com.example.navigation.splash.data

import com.example.security.storage.SecureStorage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SplashRepository @Inject constructor(
    private val secureStorage : SecureStorage
) {
    suspend fun hasSavedCredentials() : Boolean = secureStorage.hasUserData()
}