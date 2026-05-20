package com.example.network.modelDto

import kotlinx.serialization.Serializable

@Serializable
data class WordResponseDto(
    val id : Int,
    val engLang : String,
    val rusLang : String
)