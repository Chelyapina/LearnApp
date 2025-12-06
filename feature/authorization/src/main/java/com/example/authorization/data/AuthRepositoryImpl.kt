package com.example.authorization.data

import android.util.Log
import com.example.authorization.domain.entity.Auth
import com.example.authorization.domain.entity.Token
import com.example.authorization.domain.exception.AuthException
import com.example.authorization.domain.repository.AuthRepository
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor() : AuthRepository {

    override suspend fun login(credentials : Auth) : Token {
        Log.d(AuthConstants.TAG, "Attempting login for user: ${credentials.login}")

        val isValid =
                credentials.login == AuthConstants.TEST_LOGIN && credentials.password == AuthConstants.TEST_PASSWORD

        return if (isValid) {
            val token = Token("auth_token_${System.currentTimeMillis()}")
            Log.i(AuthConstants.TAG, "Login successful for ${credentials.login}")
            token
        } else {
            Log.w(AuthConstants.TAG, "Login failed: Invalid credentials")
            throw AuthException(AuthConstants.INVALID_CREDENTIALS)
        }
    }

    private var storedToken : Token? = null

    override suspend fun getToken() : Token? {
        Log.d(AuthConstants.TAG, "Getting token: ${storedToken?.toString() ?: "null"}")
        return storedToken
    }

    override suspend fun saveToken(token : Token) {
        Log.i(AuthConstants.TAG, "Token saved")
        storedToken = token
    }
}

object AuthConstants {
    const val TAG = "Auth"
    const val TEST_LOGIN = "user@test.com"
    const val TEST_PASSWORD = "0000"
    const val INVALID_CREDENTIALS = "Invalid email or password"
}