package com.example.deck.presentation.state

import com.example.deck.presentation.model.Deck
import com.example.deck.presentation.model.DeckType

sealed interface DeckScreenState {
    object Loading : DeckScreenState
    data class Error(val message: String) : DeckScreenState
    data class Content(
        val learnDeck: Deck,
        val repeatDeck: Deck,
        val currentDeckType: DeckType = DeckType.LEARN
    ) : DeckScreenState
}