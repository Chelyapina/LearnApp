package com.example.dictionary.domain.usecase

import com.example.dictionary.domain.DictionaryRepository
import javax.inject.Inject

class AddWordToDictionaryUseCase @Inject constructor(
    private val repository : DictionaryRepository
) {
    suspend operator fun invoke(wordId : Int, dictionaryId : Int) : Result<Unit> =
        repository.addWordToDictionary(wordId, dictionaryId)
}