package com.example.network.modelDto

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponseDto(
    val code: String? = null,
    val message: String? = null
)