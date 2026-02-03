package com.example.deck.data.datasource

import com.example.deck.domain.entity.User
import com.example.security.storage.SecureStorage
import javax.inject.Inject

internal class AuthLocalDataSource @Inject constructor(
    private val secureStorage : SecureStorage
) {
    suspend fun getToken() = secureStorage.getToken()
    suspend fun clearUserData() = secureStorage.clearUserData()
    suspend fun getCurrentUser() : User? {
        val userName = secureStorage.getUserName()
        return userName?.let { User(name = it) }
    }
}