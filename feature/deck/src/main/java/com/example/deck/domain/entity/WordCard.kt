package com.example.deck.domain.entity

data class WordCard(
    val id : Int,
    val status : Int,
    val originalWord : String,
    val wordTranslate : String,
    val wordTranscription : String,
)