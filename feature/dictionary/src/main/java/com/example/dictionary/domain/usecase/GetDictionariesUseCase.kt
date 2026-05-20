package com.example.dictionary.domain.usecase

import com.example.dictionary.domain.model.Dictionary
import com.example.dictionary.domain.DictionaryRepository
import javax.inject.Inject

class GetDictionariesUseCase @Inject constructor(
    private val repository : DictionaryRepository
) {
    suspend operator fun invoke() : Result<List<Dictionary>> =
        repository.getDictionaryWords()
}