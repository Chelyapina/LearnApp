package com.example.dictionary.domain.model

data class Dictionary(
    val id : Int,
    val name : String,
    val description : String?,
    val language : String,
    val ownerId : Int? = null,
    val words : List<Word> = emptyList()
)