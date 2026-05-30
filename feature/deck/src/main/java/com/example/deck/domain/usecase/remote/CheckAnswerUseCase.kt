package com.example.deck.domain.usecase.remote

import com.example.deck.domain.repository.DeckRepository
import javax.inject.Inject

class CheckAnswerUseCase @Inject constructor(
    private val repository: DeckRepository
) {
    suspend operator fun invoke(wordId: Int, userAnswer: String): Pair<Boolean, String> {
        return repository.checkAnswer(wordId, userAnswer)
    }
}