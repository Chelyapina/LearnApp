package com.example.authorization.domain.usecase

import com.example.authorization.domain.entity.Token
import com.example.authorization.domain.repository.AuthRepository
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(
    private val authRepository : AuthRepository
) {
    suspend operator fun invoke() : Token? = authRepository.getToken()
}