package com.example.deck.data.repository

import com.example.deck.data.datasource.AuthLocalDataSource
import com.example.deck.data.datasource.CompletedDeckLocalDataSource
import com.example.deck.data.datasource.DeckRemoteDataSource
import com.example.deck.domain.entity.User
import com.example.deck.domain.entity.WordCard
import com.example.deck.domain.entity.WordCompleted
import com.example.deck.domain.repository.DeckRepository
import com.example.network.modelDto.WordCompletedDto
import javax.inject.Inject

internal class DeckRepositoryImpl @Inject constructor(
    private val remoteDataSource : DeckRemoteDataSource,
    private val completedDeckLocalDataSource : CompletedDeckLocalDataSource,
    private val authLocalDataSource : AuthLocalDataSource
) : DeckRepository {
    override suspend fun getLearnDeck() : List<WordCard> {
        val token = authLocalDataSource.getToken()
        if (token.isNullOrEmpty()) {
            return emptyList()
        }

        val remoteDeck = remoteDataSource.getLearnDeck()

        val completedIds = completedDeckLocalDataSource.getCompletedDeck().map { it.id }
        val filteredDeck = remoteDeck.filter { it.id !in completedIds }

        return filteredDeck.map { dto ->
            WordCard(
                id = dto.id,
                status = dto.repetitionsCount,
                originalWord = dto.word,
                wordTranslate = dto.translation,
                wordTranscription = dto.transcription
            )
        }
    }

    override suspend fun getRepeatDeck() : List<WordCard> {
        val token = authLocalDataSource.getToken()
        if (token.isNullOrEmpty()) {
            return emptyList()
        }

        val remoteDeck = remoteDataSource.getRepeatDeck()

        val completedIds = completedDeckLocalDataSource.getCompletedDeck().map { it.id }
        val filteredDeck = remoteDeck.filter { it.id !in completedIds }

        return filteredDeck.map { dto ->
            WordCard(
                id = dto.id,
                status = dto.repetitionsCount,
                originalWord = dto.word,
                wordTranslate = dto.translation,
                wordTranscription = dto.transcription
            )
        }
    }

    override suspend fun setDeck(completedDeck : List<WordCompleted>) : Boolean {
        val token = authLocalDataSource.getToken()
        if (token.isNullOrEmpty()) {
            return false
        }

        val completedWordsDto = completedDeck.map { wordCompleted ->
            WordCompletedDto(
                id = wordCompleted.id, status = wordCompleted.status
            )
        }

        val result = remoteDataSource.saveCompletedDeck(completedWordsDto)

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

    override suspend fun logout() {
        authLocalDataSource.clearUserData()
        completedDeckLocalDataSource.clearCompletedDeck()
    }

    override suspend fun getCurrentUser() : User? = authLocalDataSource.getCurrentUser()
}