package com.example.deck.domain.usecase.local

import com.example.deck.domain.repository.DeckRepository
import javax.inject.Inject

class AddCompletedWordUseCase @Inject constructor(
    private val repository : DeckRepository
) {
    suspend operator fun invoke(wordId : Int, status : Boolean) =
            repository.addToCompletedDeck(wordId, status)
}