package com.example.network.modelDto

import kotlinx.serialization.Serializable

@Serializable
data class AnswerRequestDto(
    val wordId: Int,
    val userAnswer: String
)