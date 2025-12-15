package com.example.deck.domain.usecase.local

import com.example.deck.domain.entity.User
import com.example.deck.domain.repository.DeckRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repository : DeckRepository
) {
    suspend operator fun invoke() : User? = repository.getCurrentUser()
}