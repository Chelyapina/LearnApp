package com.example.deck.domain.usecase.remote

import com.example.deck.domain.entity.WordCompleted
import com.example.deck.domain.repository.DeckRepository
import javax.inject.Inject

class SetCompletedDeckUseCase @Inject constructor(
    private val repository : DeckRepository,
) {
    suspend operator fun invoke(completedDeck : List<WordCompleted>) : Boolean =
            repository.setDeck(completedDeck)
}