package com.example.authorization.domain.usecase

import com.example.authorization.domain.entity.Auth
import com.example.authorization.domain.entity.Token
import com.example.authorization.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository : AuthRepository
) {
    suspend operator fun invoke(credentials : Auth) : Token = authRepository.login(credentials)
}