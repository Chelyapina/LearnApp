package com.example.network.modelDto

import kotlinx.serialization.Serializable

@Serializable
data class AnswerResponseDto(
    val correct: Boolean,
    val correctAnswer: String
)