package com.example.deck.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deck.domain.usecase.local.AddCompletedWordUseCase
import com.example.deck.domain.usecase.local.ClearCompletedDeckUseCase
import com.example.deck.domain.usecase.local.GetCompletedDeckUseCase
import com.example.deck.domain.usecase.local.ShouldSendCompletedDeckUseCase
import com.example.deck.domain.usecase.remote.GetLearnDeckUseCase
import com.example.deck.domain.usecase.remote.GetRepeatDeckUseCase
import com.example.deck.domain.usecase.remote.SetCompletedDeckUseCase
import com.example.deck.presentation.model.Deck
import com.example.deck.presentation.model.DeckType
import com.example.deck.presentation.state.DeckScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeckViewModel @Inject constructor(
    private val getLearnDeckUseCase : GetLearnDeckUseCase,
    private val getRepeatDeckUseCase : GetRepeatDeckUseCase,
    private val setCompletedDeckUseCase : SetCompletedDeckUseCase,
    private val addCompletedWordUseCase : AddCompletedWordUseCase,
    private val shouldSendCompletedDeckUseCase : ShouldSendCompletedDeckUseCase,
    private val getCompletedDeckUseCase : GetCompletedDeckUseCase,
    private val clearCompletedDeckUseCase : ClearCompletedDeckUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<DeckScreenState>(DeckScreenState.Loading)
    val state : StateFlow<DeckScreenState> = _state.asStateFlow()

    private var learnDeck : Deck = Deck(DeckType.LEARN, emptyList())
    private var repeatDeck : Deck = Deck(DeckType.REPEAT, emptyList())

    init {
        loadDecks()
    }

    fun loadDecks() {
        viewModelScope.launch {
            _state.value = DeckScreenState.Loading

            try {
                val learnWords = withContext(Dispatchers.IO) {
                    getLearnDeckUseCase()
                }
                val repeatWords = withContext(Dispatchers.IO) {
                    getRepeatDeckUseCase()
                }

                learnDeck = Deck(DeckType.LEARN, learnWords)
                repeatDeck = Deck(DeckType.REPEAT, repeatWords)

                _state.value = DeckScreenState.Content(
                    learnDeck = learnDeck, repeatDeck = repeatDeck, currentDeckType = DeckType.LEARN
                )
            } catch (e : Exception) {
                _state.value = DeckScreenState.Error(
                    ERROR_LOADING_WORDS.format(
                        e.message ?: "неизвестная ошибка"
                    )
                )
            }
        }
    }

    fun switchDeck(deckType : DeckType) {
        val currentState = _state.value
        if (currentState is DeckScreenState.Content) {
            _state.value = currentState.copy(currentDeckType = deckType)
        }
    }

    fun markWordAsKnown(wordId : Int) {
        addCompletedWordUseCase(wordId, true)

        if (shouldSendCompletedDeckUseCase()) {
            viewModelScope.launch {
                setCompletedDeckUseCase(getCompletedDeckUseCase())
                clearCompletedDeckUseCase()
            }
        } else {
            moveToNextCard()
        }
    }

    fun markWordAsForgotten(wordId : Int) {
        addCompletedWordUseCase(wordId, false)

        if (shouldSendCompletedDeckUseCase()) {
            viewModelScope.launch {
                setCompletedDeckUseCase(getCompletedDeckUseCase())
                clearCompletedDeckUseCase()
            }
        } else {
            moveToNextCard()
        }
    }

    fun resetDeck(deckType : DeckType) {
        when (deckType) {
            DeckType.LEARN -> {
                learnDeck = learnDeck.copy(
                    currentIndex = 0, isCompleted = false
                )
            }

            DeckType.REPEAT -> {
                repeatDeck = repeatDeck.copy(
                    currentIndex = 0, isCompleted = false
                )
            }
        }
        updateContentState()
    }

    private fun moveToNextCard() {
        val currentState = _state.value
        if (currentState is DeckScreenState.Content) {
            when (currentState.currentDeckType) {
                DeckType.LEARN -> {
                    learnDeck = learnDeck.let { deck ->
                        if (deck.isCompleted || deck.words.isEmpty()) return@let deck

                        val newIndex = deck.currentIndex + 1
                        val isDeckCompleted = newIndex >= deck.words.size

                        deck.copy(
                            currentIndex = if (isDeckCompleted) deck.words.size else newIndex,
                            isCompleted = isDeckCompleted
                        )
                    }
                }

                DeckType.REPEAT -> {
                    repeatDeck = repeatDeck.let { deck ->
                        if (deck.isCompleted || deck.words.isEmpty()) return@let deck

                        val newIndex = deck.currentIndex + 1
                        val isDeckCompleted = newIndex >= deck.words.size

                        deck.copy(
                            currentIndex = if (isDeckCompleted) deck.words.size else newIndex,
                            isCompleted = isDeckCompleted
                        )
                    }
                }
            }
            updateContentState()
        }
    }

    private fun updateContentState() {
        val currentState = _state.value
        if (currentState is DeckScreenState.Content) {
            _state.value = currentState.copy(
                learnDeck = learnDeck, repeatDeck = repeatDeck
            )
        }
    }

    companion object {
        private const val ERROR_LOADING_WORDS = "Ошибка загрузки слов: %s"
    }
}