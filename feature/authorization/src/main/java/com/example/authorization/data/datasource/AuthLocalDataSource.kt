package com.example.authorization.data.datasource

import com.example.authorization.domain.entity.User
import com.example.security.storage.SecureStorage
import javax.inject.Inject

internal class AuthLocalDataSource @Inject constructor(
    private val secureStorage : SecureStorage
) {
    suspend fun saveUserData(user : User) =
            secureStorage.saveUserData(user.name, user.getToken().getValue())

    suspend fun getToken() : String? = secureStorage.getToken()
}