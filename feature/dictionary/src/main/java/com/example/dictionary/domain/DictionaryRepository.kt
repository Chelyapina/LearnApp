package com.example.dictionary.domain

import com.example.dictionary.domain.model.Dictionary
import com.example.dictionary.domain.model.SearchWordResult

interface DictionaryRepository {
    suspend fun createDictionary(
        name : String,
        description : String?,
        language : String
    ) : Result<Dictionary>

    suspend fun addWordToDictionary(wordId : Int, dictionaryId : Int) : Result<Unit>
    suspend fun removeWordFromDictionary(dictionaryId : Int, wordId : Int) : Result<Unit>
    suspend fun deleteDictionary(dictionaryId : Int) : Result<Unit>
    suspend fun searchWords(prefix : String) : Result<List<SearchWordResult>>
    suspend fun getDictionaryWords() : Result<List<Dictionary>>
}