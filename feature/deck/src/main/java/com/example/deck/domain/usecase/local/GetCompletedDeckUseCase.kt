package com.example.deck.domain.usecase.local

import com.example.deck.data.CompletedDeckLocalDataSource
import com.example.deck.domain.entity.WordCompleted
import javax.inject.Inject

class GetCompletedDeckUseCase @Inject constructor(
    private val completedDeckLocalDataSource : CompletedDeckLocalDataSource
) {
    operator fun invoke() : List<WordCompleted> = completedDeckLocalDataSource.getCompletedDeck()
}