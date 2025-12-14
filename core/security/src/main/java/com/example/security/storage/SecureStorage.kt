package com.example.security.storage

interface SecureStorage {
    suspend fun getToken(): String?
    suspend fun getUserName(): String?
    suspend fun saveUserData(userName: String, token: String)
    suspend fun clearUserData()
    suspend fun hasUserData(): Boolean
}