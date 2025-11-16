package com.example.deck.presentation.mapper

import com.example.deck.domain.entity.WordCard
import com.example.deck.presentation.model.WordCardUI

object WordCardMapper {

    fun toUI(wordCard: WordCard): WordCardUI {
        return WordCardUI(
            id = wordCard.id,
            originalWord = wordCard.originalWord,
            wordTranslate = wordCard.wordTranslate,
            wordTranscription = formatTranscription(wordCard.wordTranscription)
        )
    }

    fun toUIList(wordCards: List<WordCard>): List<WordCardUI> {
        return wordCards.map { toUI(it) }
    }

    private fun formatTranscription(transcription: String): String {
        return transcription
            .removePrefix("/")
            .removeSuffix("/")
            .let { "[$it]" }
    }
}