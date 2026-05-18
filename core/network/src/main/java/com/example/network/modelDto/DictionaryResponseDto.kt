package com.example.network.modelDto

import kotlinx.serialization.Serializable

@Serializable
data class DictionaryResponseDto(
    val id : Int,
    val name : String,
    val words : List<WordResponseDto>,
    val ownerId : Int? = null
)