package com.example.deck.data.repository

import com.example.deck.data.datasource.CompletedDeckLocalDataSource
import com.example.deck.data.datasource.DeckRemoteMockDataSource
import com.example.deck.domain.entity.WordCard
import com.example.deck.domain.entity.WordCompleted
import com.example.deck.domain.repository.DeckRepository
import javax.inject.Inject

internal class DeckRepositoryImpl @Inject constructor(
    private val localDataSource : DeckRemoteMockDataSource,
    private val completedDeckLocalDataSource : CompletedDeckLocalDataSource
) : DeckRepository {
    override suspend fun getLearnDeck() : List<WordCard> {
        val completedIds = completedDeckLocalDataSource.getCompletedDeck().map { it.id }
        return localDataSource.getLearnDeck().filter { it.id !in completedIds }
    }

    override suspend fun getRepeatDeck() : List<WordCard> {
        val completedIds = completedDeckLocalDataSource.getCompletedDeck().map { it.id }
        return localDataSource.getRepeatDeck().filter { it.id !in completedIds }
    }

    override suspend fun setDeck(completedDeck : List<WordCompleted>) : Boolean {
        val result = localDataSource.saveCompletedDeck(completedDeck)
        if (result) {
            completedDeckLocalDataSource.clearCompletedDeck()
        }
        return result
    }

    override suspend fun getCompletedDeck() : List<WordCompleted> =
            completedDeckLocalDataSource.getCompletedDeck()

    override suspend fun clearCompletedDeck() = completedDeckLocalDataSource.clearCompletedDeck()

    override suspend fun addToCompletedDeck(wordId : Int, status : Boolean) =
            completedDeckLocalDataSource.addCompletedWord(wordId, status)

    override suspend fun shouldSendCompletedDeck() : Boolean =
            completedDeckLocalDataSource.hasReachedLimit()
}