package com.example.dictionary.domain.usecase

import com.example.dictionary.domain.DictionaryRepository
import javax.inject.Inject

class DeleteDictionaryUseCase @Inject constructor(
    private val repository : DictionaryRepository
) {
    suspend operator fun invoke(dictionaryId : Int) : Result<Unit> =
        repository.deleteDictionary(dictionaryId)
}