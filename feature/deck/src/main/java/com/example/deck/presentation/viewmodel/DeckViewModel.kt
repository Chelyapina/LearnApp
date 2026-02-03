package com.example.deck.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deck.domain.usecase.local.GetCurrentUserUseCase
import com.example.deck.domain.usecase.local.LogOutUseCase
import com.example.deck.domain.usecase.local.AddCompletedWordUseCase
import com.example.deck.domain.usecase.local.ClearCompletedDeckUseCase
import com.example.deck.domain.usecase.local.GetCompletedDeckUseCase
import com.example.deck.domain.usecase.local.ShouldSendCompletedDeckUseCase
import com.example.deck.domain.usecase.remote.GetLearnDeckUseCase
import com.example.deck.domain.usecase.remote.GetRepeatDeckUseCase
import com.example.deck.domain.usecase.remote.SetCompletedDeckUseCase
import com.example.deck.presentation.model.Deck
import com.example.deck.presentation.model.DeckType
import com.example.deck.presentation.state.DeckEvent
import com.example.deck.presentation.state.DeckNavigationEvent
import com.example.deck.presentation.state.DeckScreen
import com.example.deck.presentation.state.DeckUiState
import com.example.designsystem.components.alert.model.AlertData
import com.example.designsystem.state.LoadError
import com.example.designsystem.state.LoadingState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeckViewModel @Inject constructor(
    private val getLearnDeckUseCase : GetLearnDeckUseCase,
    private val getRepeatDeckUseCase : GetRepeatDeckUseCase,
    private val setCompletedDeckUseCase : SetCompletedDeckUseCase,
    private val addCompletedWordUseCase : AddCompletedWordUseCase,
    private val shouldSendCompletedDeckUseCase : ShouldSendCompletedDeckUseCase,
    private val getCompletedDeckUseCase : GetCompletedDeckUseCase,
    private val clearCompletedDeckUseCase : ClearCompletedDeckUseCase,
    private val logOutUseCase : LogOutUseCase,
    private val getCurrentUserUseCase : GetCurrentUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DeckUiState(isLoading = LoadingState.Idle))
    val uiState : StateFlow<DeckUiState> = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<DeckNavigationEvent>()
    val navigationEvent : SharedFlow<DeckNavigationEvent> = _navigationEvent.asSharedFlow()

    init {
        handleEvent(DeckEvent.LoadDecks)
    }

    fun handleEvent(event : DeckEvent) {
        when (event) {
            DeckEvent.LoadDecks -> loadDecks()
            is DeckEvent.SwitchDeck -> switchDeck(event.deckType)
            is DeckEvent.MarkWord -> markWord(event.wordId, event.isKnown)
            is DeckEvent.ResetDeck -> resetDeck(event.deckType)
            DeckEvent.Logout -> logout()
            DeckEvent.AlertHandled -> handleAlertDismissed()
        }
    }

    private fun loadDecks() {
        _uiState.update { it.copy(isLoading = LoadingState.Loading) }

        viewModelScope.launch {
            try {
                val learnWordsDeferred = async { getLearnDeckUseCase() }
                val repeatWordsDeferred = async { getRepeatDeckUseCase() }
                val userDeferred = async { getCurrentUserUseCase() }

                val learnWords = learnWordsDeferred.await()
                val repeatWords = repeatWordsDeferred.await()
                val user = userDeferred.await()

                val learnDeck = Deck(DeckType.LEARN, learnWords)
                val repeatDeck = Deck(DeckType.REPEAT, repeatWords)

                _uiState.update { state ->
                    state.copy(
                        screen = DeckScreen.Content(currentDeckType = DeckType.LEARN),
                        learnDeck = learnDeck,
                        repeatDeck = repeatDeck,
                        user = user,
                        isLoading = LoadingState.Success
                    )
                }
            } catch (e : Exception) {
                _uiState.update { state ->
                    state.copy(
                        screen = DeckScreen.Error(
                            message = ERROR_LOADING_WORDS.format(e.message ?: UNKNOWN_ERROR)
                        ), isLoading = LoadingState.Error(LoadError.GenericError)
                    )
                }
            }
        }
    }

    private fun switchDeck(deckType: DeckType) {
        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState.screen is DeckScreen.Content) {
                _uiState.update { state ->
                    state.copy(
                        screen = DeckScreen.Content(
                            currentDeckType = deckType
                        )
                    )
                }
            }
        }
    }

    private fun markWord(wordId: Int, isKnown: Boolean) {
        viewModelScope.launch {
            try {
                addCompletedWordUseCase(wordId, isKnown)

                if (shouldSendCompletedDeckUseCase()) {
                    setCompletedDeckUseCase(getCompletedDeckUseCase())
                    clearCompletedDeckUseCase()
                } else {
                    moveToNextCard()
                }
            } catch (e: Exception) {
                _uiState.update { state ->
                    state.copy(
                        alertData = createAlertData("Ошибка", ERROR_SAVING_WORD.format(e.message))
                    )
                }
            }
        }
    }

    private fun resetDeck(deckType : DeckType) {
        val currentState = _uiState.value

        when (deckType) {
            DeckType.LEARN -> {
                val updatedDeck = currentState.learnDeck.copy(
                    currentIndex = 0, isCompleted = false
                )
                _uiState.update { state ->
                    state.copy(
                        learnDeck = updatedDeck
                    )
                }
            }
            DeckType.REPEAT -> {
                val updatedDeck = currentState.repeatDeck.copy(
                    currentIndex = 0, isCompleted = false
                )
                _uiState.update { state ->
                    state.copy(
                        repeatDeck = updatedDeck
                    )
                }
            }
        }
    }

    private fun moveToNextCard() {
        val currentState = _uiState.value
        val currentScreen = currentState.screen

        if (currentScreen is DeckScreen.Content) {
            val deckType = currentScreen.currentDeckType

            when (deckType) {
                DeckType.LEARN -> {
                    val updatedDeck = currentState.learnDeck.let { deck ->
                        if (deck.isCompleted || deck.words.isEmpty()) return@let deck

                        val newIndex = deck.currentIndex + 1
                        val isDeckCompleted = newIndex >= deck.words.size

                        deck.copy(
                            currentIndex = if (isDeckCompleted) deck.words.size else newIndex,
                            isCompleted = isDeckCompleted
                        )
                    }
                    _uiState.update { state ->
                        state.copy(learnDeck = updatedDeck)
                    }
                }
                DeckType.REPEAT -> {
                    val updatedDeck = currentState.repeatDeck.let { deck ->
                        if (deck.isCompleted || deck.words.isEmpty()) return@let deck

                        val newIndex = deck.currentIndex + 1
                        val isDeckCompleted = newIndex >= deck.words.size

                        deck.copy(
                            currentIndex = if (isDeckCompleted) deck.words.size else newIndex,
                            isCompleted = isDeckCompleted
                        )
                    }
                    _uiState.update { state ->
                        state.copy(repeatDeck = updatedDeck)
                    }
                }
            }
        }
    }

    private fun logout() {
        _uiState.update { it.copy(isLoading = LoadingState.Loading) }

        viewModelScope.launch {
            try {
                logOutUseCase()

                _uiState.update { state ->
                    state.copy(isLoading = LoadingState.Success)
                }

                _navigationEvent.emit(DeckNavigationEvent.NavigateToAuth)
            } catch (e : Exception) {
                _uiState.update { state ->
                    state.copy(
                        isLoading = LoadingState.Error(LoadError.GenericError),
                        alertData = createAlertData(
                            LOGOUT_ERROR_TITLE, e.message ?: UNKNOWN_ERROR
                        )
                    )
                }
            }
        }
    }

    private fun handleAlertDismissed() {
        _uiState.update { state ->
            state.copy(alertData = null)
        }
    }

    private fun createAlertData(title : String, message : String) : AlertData {
        return AlertData(
            title = title,
            message = message,
            confirmText = ALERT_CONFIRM,
            onConfirm = { handleEvent(DeckEvent.AlertHandled) })
    }

    companion object {
        private const val ERROR_LOADING_WORDS = "Ошибка загрузки слов: %s"
        private const val UNKNOWN_ERROR = "неизвестная ошибка"
        private const val ERROR_SAVING_WORD = "Ошибка при сохранении слова: %s"
        private const val LOGOUT_ERROR_TITLE = "Ошибка выхода"
        private const val ALERT_CONFIRM = "OK"
    }
}