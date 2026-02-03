package com.example.deck.domain.repository

import com.example.deck.domain.entity.User
import com.example.deck.domain.entity.WordCard
import com.example.deck.domain.entity.WordCompleted

interface DeckRepository {
    suspend fun getLearnDeck() : List<WordCard>
    suspend fun getRepeatDeck() : List<WordCard>
    suspend fun setDeck(completedDeck : List<WordCompleted>) : Boolean
    suspend fun getCompletedDeck() : List<WordCompleted>
    suspend fun clearCompletedDeck()
    suspend fun addToCompletedDeck(wordId : Int, status : Boolean)
    suspend fun shouldSendCompletedDeck() : Boolean
    suspend fun logout()
    suspend fun getCurrentUser() : User?
}