package com.example.deck.data.converter

import com.example.deck.domain.entity.WordCompleted
import com.example.storage.model.WordCompletedStorage

object CompletedDeckConverter {
    fun convertToStorage(domainModels : List<WordCompleted>) : List<WordCompletedStorage> =
            domainModels.map { convertToStorage(it) }

    fun convertToStorage(domainModel : WordCompleted) : WordCompletedStorage = WordCompletedStorage(
        id = domainModel.id, status = domainModel.status
    )

    fun convertToDomain(storageModels : List<WordCompletedStorage>) : List<WordCompleted> =
            storageModels.map { convertToDomain(it) }

    fun convertToDomain(storageModel : WordCompletedStorage) : WordCompleted = WordCompleted(
        id = storageModel.id, status = storageModel.status
    )
}