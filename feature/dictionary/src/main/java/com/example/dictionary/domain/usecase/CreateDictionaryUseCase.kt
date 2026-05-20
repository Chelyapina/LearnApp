package com.example.dictionary.domain.usecase

import com.example.dictionary.domain.model.Dictionary
import com.example.dictionary.domain.DictionaryRepository
import javax.inject.Inject

class CreateDictionaryUseCase @Inject constructor(
    private val repository : DictionaryRepository
) {
    suspend operator fun invoke(
        name : String,
        description : String?,
        language : String
    ) : Result<Dictionary> {
        return repository.createDictionary(name, description, language)
    }
}