package com.example.deck.domain.usecase.local

import com.example.deck.data.CompletedDeckLocalDataSource
import javax.inject.Inject

class AddCompletedWordUseCase @Inject constructor(
    private val completedDeckLocalDataSource : CompletedDeckLocalDataSource
) {
    operator fun invoke(wordId : Int, status : Boolean) =
            completedDeckLocalDataSource.addCompletedWord(wordId, status)
}