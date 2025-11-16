package com.example.deck.data

import com.example.deck.domain.entity.WordCard
import com.example.deck.domain.entity.WordCompleted
import com.example.deck.domain.repository.DeckRepository
import javax.inject.Inject

class DeckRepositoryImpl @Inject constructor(
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

    override fun getCompletedDeck() : List<WordCompleted> {
        return completedDeckLocalDataSource.getCompletedDeck()
    }

    override fun clearCompletedDeck() {
        completedDeckLocalDataSource.clearCompletedDeck()
    }

    override fun addToCompletedDeck(wordId : Int, status : Boolean) {
        completedDeckLocalDataSource.addCompletedWord(wordId, status)
    }

    override fun shouldSendCompletedDeck() : Boolean {
        return completedDeckLocalDataSource.hasReachedLimit()
    }
}