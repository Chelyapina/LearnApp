package com.example.deck.domain.usecase.local

import com.example.deck.domain.entity.WordCompleted
import com.example.deck.domain.repository.DeckRepository
import javax.inject.Inject

class GetCompletedDeckUseCase @Inject constructor(
    private val repository : DeckRepository
) {
    suspend operator fun invoke() : List<WordCompleted> = repository.getCompletedDeck()
}