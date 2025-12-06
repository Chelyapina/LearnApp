package com.example.storage.model

import kotlinx.serialization.Serializable

@Serializable
data class WordCompletedStorage(
    val id : Int, val status : Int
)