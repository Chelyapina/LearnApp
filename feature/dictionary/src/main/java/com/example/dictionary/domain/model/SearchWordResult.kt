package com.example.dictionary.domain.model

data class SearchWordResult(
    val id : Int,
    val engLang : String,
    val rusLang : String,
    val transcription : String?
)