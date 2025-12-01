package com.example.authorization.domain.repository

import com.example.authorization.domain.entity.Auth
import com.example.authorization.domain.entity.Token

interface AuthRepository {
    suspend fun login(credentials : Auth) : Token
    suspend fun getToken() : Token?
    suspend fun saveToken(token : Token)
}