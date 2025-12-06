package com.example.deck.domain.usecase.local

import com.example.deck.domain.repository.DeckRepository
import javax.inject.Inject

class ClearCompletedDeckUseCase @Inject constructor(
    private val repository : DeckRepository
) {
    suspend operator fun invoke() = repository.clearCompletedDeck()
}