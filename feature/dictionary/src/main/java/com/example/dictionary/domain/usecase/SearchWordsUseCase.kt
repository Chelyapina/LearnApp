package com.example.dictionary.domain.usecase

import com.example.dictionary.domain.model.SearchWordResult
import com.example.dictionary.domain.DictionaryRepository
import javax.inject.Inject

class SearchWordsUseCase @Inject constructor(
    private val repository : DictionaryRepository
) {
    suspend operator fun invoke(prefix : String) : Result<List<SearchWordResult>> =
        repository.searchWords(prefix)
}