package com.example.authorization.domain.repository

import com.example.authorization.domain.entity.Auth
import com.example.authorization.domain.entity.Token
import com.example.authorization.domain.entity.User

interface AuthRepository {
    suspend fun login(credentials : Auth) : User
    suspend fun getToken() : Token?
    suspend fun saveUser(user : User)
}