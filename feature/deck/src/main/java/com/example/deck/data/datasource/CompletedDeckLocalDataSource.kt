package com.example.deck.data.datasource

import com.example.deck.data.converter.CompletedDeckConverter
import com.example.deck.domain.entity.WordCompleted
import com.example.deck.domain.util.WordProgressCalculator
import com.example.storage.storage.CompletedDeckStorage
import javax.inject.Inject

internal class CompletedDeckLocalDataSource @Inject constructor(
    private val completedDeckStorage : CompletedDeckStorage
) {
    suspend fun getCompletedDeck() : List<WordCompleted> {
        val storageModels = completedDeckStorage.getCompletedDeck()
        return CompletedDeckConverter.convertToDomain(storageModels)
    }

    suspend fun saveCompletedDeck(deck : List<WordCompleted>) {
        val storageModels = CompletedDeckConverter.convertToStorage(deck)
        completedDeckStorage.saveCompletedDeck(storageModels)
    }

    suspend fun clearCompletedDeck() {
        completedDeckStorage.clearCompletedDeck()
    }

    suspend fun addCompletedWord(wordId : Int, isKnown : Boolean) {
        val currentDeck = getCompletedDeck()
        val currentStatus = currentDeck.find { it.id == wordId }?.status ?: 0
        val newStatus = WordProgressCalculator.calculateNewStatus(currentStatus, isKnown)

        val updatedDeck =
                currentDeck.filter { it.id != wordId }.plus(WordCompleted(wordId, newStatus))

        saveCompletedDeck(updatedDeck)
    }

    suspend fun hasReachedLimit() : Boolean {
        return getCompletedDeck().size >= LIMIT
    }

    private companion object {
        const val LIMIT = 10
    }
}