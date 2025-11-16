package com.example.deck.domain.usecase.local

import com.example.deck.data.CompletedDeckLocalDataSource
import javax.inject.Inject

class ClearCompletedDeckUseCase @Inject constructor(
    private val completedDeckLocalDataSource : CompletedDeckLocalDataSource
) {
    operator fun invoke() = completedDeckLocalDataSource.clearCompletedDeck()
}