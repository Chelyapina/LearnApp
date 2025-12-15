package com.example.authorization.domain.usecase

import com.example.authorization.domain.entity.User
import com.example.authorization.domain.repository.AuthRepository
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val authRepository : AuthRepository
) {
    suspend operator fun invoke(user : User) = authRepository.saveUser(user)
}