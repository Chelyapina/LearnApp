package com.example.deck.data.datasource

import com.example.network.ApiService
import com.example.network.exception.ExceptionMapper
import com.example.network.exception.NetworkException
import com.example.network.modelDto.WordCardDto
import com.example.network.modelDto.WordCompletedDto
import javax.inject.Inject
import com.example.network.modelDto.AnswerRequestDto
import com.example.network.modelDto.AnswerResponseDto

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

    suspend fun checkAnswer(request: AnswerRequestDto) : AnswerResponseDto {
        return try {
            val token = getAuthToken()
            val response = deckApiService.checkAnswer("Bearer $token", request)

            if (response.isSuccessful && response.body() != null) {
                response.body()!!
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
        return authLocalDataSource.getToken() ?: throw NetworkException.UnauthorizedError(null)
    }
}