package com.example.network.modelDto

import kotlinx.serialization.Serializable

@Serializable
data class CreateDictionaryRequestDto(
    val name : String,
    val description : String? = null,
    val language : String
)