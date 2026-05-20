package com.example.network.modelDto

import kotlinx.serialization.Serializable

@Serializable
data class AddWordToDictionaryRequestDto(
    val wordId : Int,
    val dictionaryId : Int
)