package com.example.security.storage

interface SecureStorage {
    suspend fun saveToken(token: String)
    suspend fun getToken(): String?
    suspend fun clearToken()
}