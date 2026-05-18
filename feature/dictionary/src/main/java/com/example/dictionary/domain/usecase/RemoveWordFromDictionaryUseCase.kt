package com.example.dictionary.domain.usecase

import com.example.dictionary.domain.DictionaryRepository
import javax.inject.Inject

class RemoveWordFromDictionaryUseCase @Inject constructor(
    private val repository : DictionaryRepository
) {
    suspend operator fun invoke(dictionaryId : Int, wordId : Int) : Result<Unit> =
            repository.removeWordFromDictionary(dictionaryId, wordId)
}