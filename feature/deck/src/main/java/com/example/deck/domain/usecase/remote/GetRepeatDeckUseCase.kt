package com.example.deck.domain.usecase.remote

import com.example.deck.domain.repository.DeckRepository
import com.example.deck.presentation.mapper.WordCardMapper
import com.example.deck.presentation.model.WordCardUI
import javax.inject.Inject

class GetRepeatDeckUseCase @Inject constructor(private val repository : DeckRepository) {
    suspend operator fun invoke(): List<WordCardUI> {
        val wordCards = repository.getRepeatDeck()
        return WordCardMapper.toUIList(wordCards)
    }
}