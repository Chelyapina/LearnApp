package com.example.network.modelDto

import kotlinx.serialization.Serializable

@Serializable
data class SearchWordResponseDto(
    val id : Int,
    val engLang : String,
    val rusLang : String,
    val transcription : String? = null
)