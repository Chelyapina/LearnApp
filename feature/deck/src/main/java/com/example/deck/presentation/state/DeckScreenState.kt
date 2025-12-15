package com.example.deck.presentation.state

import com.example.deck.domain.entity.User
import com.example.deck.presentation.model.Deck
import com.example.deck.presentation.model.DeckType
import com.example.designsystem.components.alert.model.AlertData
import com.example.designsystem.state.LoadingState

sealed class DeckScreen {
    data object Loading : DeckScreen()
    data class Content(
        val currentDeckType: DeckType = DeckType.LEARN
    ) : DeckScreen()

    data class Error(
        val message : String
    ) : DeckScreen()
}

data class DeckUiState(
    val screen : DeckScreen = DeckScreen.Loading,

    val learnDeck : Deck = Deck(DeckType.LEARN, emptyList()),
    val repeatDeck : Deck = Deck(DeckType.REPEAT, emptyList()),
    val user: User? = null,

    val isLoading : LoadingState,

    val alertData : AlertData? = null,
)

sealed class DeckEvent {
    data object LoadDecks : DeckEvent()
    data class SwitchDeck(val deckType : DeckType) : DeckEvent()
    data class MarkWord(val wordId: Int, val isKnown: Boolean) : DeckEvent()
    data class ResetDeck(val deckType : DeckType) : DeckEvent()
    data object Logout : DeckEvent()
    data object AlertHandled : DeckEvent()
}

sealed interface DeckNavigationEvent {
    data object NavigateToAuth : DeckNavigationEvent
    data object NavigateBack : DeckNavigationEvent
}