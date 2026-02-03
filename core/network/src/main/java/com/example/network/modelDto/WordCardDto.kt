package com.example.network.modelDto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WordCardDto(
    @SerialName("id")
    val id: Int,

    @SerialName("engLang")
    val word: String,

    @SerialName("transcription")
    val transcription: String,

    @SerialName("rusLang")
    val translation: String,

    @SerialName("studyLvl")
    val repetitionsCount: Int = 0
)