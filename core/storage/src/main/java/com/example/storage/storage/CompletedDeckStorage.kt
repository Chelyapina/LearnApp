package com.example.storage.storage

import com.example.storage.model.WordCompletedStorage

interface CompletedDeckStorage {
    suspend fun saveCompletedDeck(deck : List<WordCompletedStorage>)
    suspend fun getCompletedDeck() : List<WordCompletedStorage>
    suspend fun clearCompletedDeck()
}