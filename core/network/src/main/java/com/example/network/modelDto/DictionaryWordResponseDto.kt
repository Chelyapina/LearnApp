package com.example.network.modelDto

import kotlinx.serialization.Serializable

@Serializable
data class DictionaryWordResponseDto(
    val dictionaryId : Int,
    val word : WordResponseDto
)