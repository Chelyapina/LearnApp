package com.example.network.modelDto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompletedDeckDto(
    @SerialName("completedWords")
    val completedWords: List<WordCompletedDto>
)