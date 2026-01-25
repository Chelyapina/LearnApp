package com.example.deck.data.datasource

import com.example.network.ApiService
import com.example.network.exception.ExceptionMapper
import com.example.network.exception.NetworkException
import com.example.network.modelDto.WordCardDto
import com.example.network.modelDto.WordCompletedDto
import javax.inject.Inject

internal class DeckRemoteDataSource @Inject constructor(
    private val deckApiService : ApiService, private val authLocalDataSource : AuthLocalDataSource
) {

    suspend fun getLearnDeck() : List<WordCardDto> {
        return try {
            val token = getAuthToken()
            val response = deckApiService.getLearnDeck("Bearer $token")

            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                throw ExceptionMapper.mapToNetworkException(
                    Exception("HTTP ${response.code()}: ${response.message()}")
                )
            }
        } catch (e : Exception) {
            val networkException = ExceptionMapper.mapToNetworkException(e)
            throw networkException
        }
    }

    suspend fun getRepeatDeck() : List<WordCardDto> {
        return try {
            val token = getAuthToken()
            val response = deckApiService.getRepeatDeck("Bearer $token")

            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                throw ExceptionMapper.mapToNetworkException(
                    Exception("HTTP ${response.code()}: ${response.message()}")
                )
            }
        } catch (e : Exception) {
            val networkException = ExceptionMapper.mapToNetworkException(e)
            throw networkException
        }
    }

    suspend fun saveCompletedDeck(completedWords : List<WordCompletedDto>) : Boolean {
        return try {
            val token = getAuthToken()
            val response = deckApiService.saveCompletedDeck("Bearer $token", completedWords)

            if (response.isSuccessful) {
                true
            } else {
                throw ExceptionMapper.mapToNetworkException(
                    Exception("HTTP ${response.code()}: ${response.message()}")
                )
            }
        } catch (e : Exception) {
            val networkException = ExceptionMapper.mapToNetworkException(e)
            throw networkException
        }
    }

    private suspend fun getAuthToken() : String {
        return authLocalDataSource.getToken() ?: throw NetworkException.UnauthorizedError
    }
}