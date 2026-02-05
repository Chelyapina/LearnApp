package com.example.models

interface AuthDataSource {
    suspend fun hasValidCredentials(): Boolean
}