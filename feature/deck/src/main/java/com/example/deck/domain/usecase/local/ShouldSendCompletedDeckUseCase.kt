package com.example.deck.domain.usecase.local

import com.example.deck.domain.repository.DeckRepository
import javax.inject.Inject

class ShouldSendCompletedDeckUseCase @Inject constructor(
    private val repository : DeckRepository
) {
    suspend operator fun invoke() : Boolean = repository.shouldSendCompletedDeck()
}