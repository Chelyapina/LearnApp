package com.example.dictionary.data

import com.example.dictionary.domain.model.Dictionary
import com.example.dictionary.domain.model.DictionaryWord
import com.example.dictionary.domain.model.SearchWordResult
import com.example.dictionary.domain.model.Word
import com.example.network.modelDto.AddWordToDictionaryRequestDto
import com.example.network.ApiService
import com.example.network.modelDto.CreateDictionaryRequestDto
import com.example.network.modelDto.DictionaryResponseDto
import com.example.network.modelDto.DictionaryWordResponseDto
import com.example.network.modelDto.SearchWordResponseDto
import com.example.network.exception.ExceptionMapper
import com.example.network.exception.NetworkException
import com.example.security.storage.SecureStorage
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

internal class DictionaryRemoteDataSource @Inject constructor(
    private val apiService : ApiService, private val secureStorage : SecureStorage
) {

    suspend fun createDictionary(
        name : String,
        description : String?,
        language : String
    ) : Dictionary {
        return try {
            val token = getAuthToken()
            val request = CreateDictionaryRequestDto(name, description, language)
            val response : Response<Unit> = apiService.createDictionary(
                token = "Bearer $token", request = request
            )

            if (response.isSuccessful) {
                Dictionary(
                    id = 0,
                    name = name,
                    description = description,
                    language = language,
                    ownerId = null,
                    words = emptyList()
                )
            } else {
                throw HttpException(response)
            }
        } catch (e : Exception) {
            throw ExceptionMapper.mapToNetworkException(e)
        }
    }

    suspend fun addWordToDictionary(wordId : Int, dictionaryId : Int) {
        try {
            val token = getAuthToken()
            val request = AddWordToDictionaryRequestDto(wordId, dictionaryId)
            val response : Response<Unit> = apiService.addWordToDictionary(
                token = "Bearer $token", request = request
            )

            if (!response.isSuccessful) {
                throw HttpException(response)
            }
        } catch (e : Exception) {
            throw ExceptionMapper.mapToNetworkException(e)
        }
    }

    suspend fun removeWordFromDictionary(dictionaryId : Int, wordId : Int) {
        try {
            val token = getAuthToken()
            val response : Response<Unit> = apiService.removeWordFromDictionary(
                token = "Bearer $token", dictionaryId = dictionaryId, wordId = wordId
            )

            if (!response.isSuccessful) {
                throw HttpException(response)
            }
        } catch (e : Exception) {
            throw ExceptionMapper.mapToNetworkException(e)
        }
    }

    suspend fun deleteDictionary(dictionaryId : Int) {
        try {
            val token = getAuthToken()
            val response : Response<Unit> = apiService.deleteDictionary(
                token = "Bearer $token", dictionaryId = dictionaryId
            )

            if (!response.isSuccessful) {
                throw HttpException(response)
            }
        } catch (e : Exception) {
            throw ExceptionMapper.mapToNetworkException(e)
        }
    }

    suspend fun searchWords(prefix : String) : List<SearchWordResult> {
        return try {
            val token = getAuthToken()
            val response : Response<List<SearchWordResponseDto>> = apiService.searchWords(
                token = "Bearer $token", prefix = prefix
            )

            if (response.isSuccessful && response.body() != null) {
                response.body()!!.map { it.toDomain() }
            } else {
                throw HttpException(response)
            }
        } catch (e : Exception) {
            throw ExceptionMapper.mapToNetworkException(e)
        }
    }

    suspend fun getDictionaryWords() : List<Dictionary> {
        return try {
            val token = getAuthToken()
            val response : Response<List<DictionaryResponseDto>> = apiService.getDictionaryWords(
                token = "Bearer $token"
            )

            if (response.isSuccessful && response.body() != null) {
                response.body()!!.map { it.toDomain() }
            } else {
                throw HttpException(response)
            }
        } catch (e : Exception) {
            throw ExceptionMapper.mapToNetworkException(e)
        }
    }

    private suspend fun getAuthToken() : String {
        return secureStorage.getToken() ?: throw NetworkException.UnauthorizedError(null)
    }

    private fun DictionaryResponseDto.toDomain() : Dictionary {
        return Dictionary(
            id = id,
            name = name,
            description = null,
            language = "",
            ownerId = ownerId,
            words = words.map { wordDto ->
                Word(
                    id = wordDto.id, engLang = wordDto.engLang, rusLang = wordDto.rusLang
                )
            })
    }

    private fun SearchWordResponseDto.toDomain() : SearchWordResult {
        return SearchWordResult(
            id = id, engLang = engLang, rusLang = rusLang, transcription = transcription
        )
    }

    private fun DictionaryWordResponseDto.toDomain() : DictionaryWord {
        return DictionaryWord(
            dictionaryId = dictionaryId, word = Word(
                id = word.id,
                engLang = word.engLang,
                rusLang = word.rusLang,
            )
        )
    }
}