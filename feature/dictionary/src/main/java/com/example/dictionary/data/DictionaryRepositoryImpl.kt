package com.example.dictionary.data

import com.example.dictionary.domain.model.Dictionary
import com.example.dictionary.domain.DictionaryRepository
import com.example.dictionary.domain.model.SearchWordResult
import javax.inject.Inject

internal class DictionaryRepositoryImpl @Inject constructor(
    private val remoteDataSource : DictionaryRemoteDataSource
) : DictionaryRepository {

    override suspend fun createDictionary(
        name : String,
        description : String?,
        language : String
    ) : Result<Dictionary> {
        return try {
            val dictionary = remoteDataSource.createDictionary(name, description, language)
            Result.success(dictionary)
        } catch (e : Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addWordToDictionary(wordId : Int, dictionaryId : Int) : Result<Unit> {
        return try {
            remoteDataSource.addWordToDictionary(wordId, dictionaryId)
            Result.success(Unit)
        } catch (e : Exception) {
            Result.failure(e)
        }
    }

    override suspend fun removeWordFromDictionary(dictionaryId : Int, wordId : Int) : Result<Unit> {
        return try {
            remoteDataSource.removeWordFromDictionary(dictionaryId, wordId)
            Result.success(Unit)
        } catch (e : Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteDictionary(dictionaryId : Int) : Result<Unit> {
        return try {
            remoteDataSource.deleteDictionary(dictionaryId)
            Result.success(Unit)
        } catch (e : Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchWords(prefix : String) : Result<List<SearchWordResult>> {
        return try {
            val results = remoteDataSource.searchWords(prefix)
            Result.success(results)
        } catch (e : Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getDictionaryWords() : Result<List<Dictionary>> {
        return try {
            val dictionaries = remoteDataSource.getDictionaryWords()
            Result.success(dictionaries)
        } catch (e : Exception) {
            Result.failure(e)
        }
    }
}