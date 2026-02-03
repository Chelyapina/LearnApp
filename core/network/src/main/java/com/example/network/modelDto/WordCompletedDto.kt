package com.example.network.modelDto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WordCompletedDto(
    @SerialName("wordId")
    val id: Int,

    @SerialName("studyLevel")
    val status: Int
)