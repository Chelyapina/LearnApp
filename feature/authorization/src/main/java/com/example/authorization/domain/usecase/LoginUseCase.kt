package com.example.authorization.domain.usecase

import com.example.authorization.domain.entity.Auth
import com.example.authorization.domain.entity.User
import com.example.authorization.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository : AuthRepository
) {
    suspend operator fun invoke(credentials : Auth) : User = authRepository.login(credentials)
}