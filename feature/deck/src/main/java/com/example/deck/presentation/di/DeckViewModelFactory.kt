package com.example.deck.presentation.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.deck.domain.usecase.local.AddCompletedWordUseCase
import com.example.deck.domain.usecase.local.ClearCompletedDeckUseCase
import com.example.deck.domain.usecase.local.GetCompletedDeckUseCase
import com.example.deck.domain.usecase.local.GetCurrentUserUseCase
import com.example.deck.domain.usecase.local.LogOutUseCase
import com.example.deck.domain.usecase.local.ShouldSendCompletedDeckUseCase
import com.example.deck.domain.usecase.remote.GetLearnDeckUseCase
import com.example.deck.domain.usecase.remote.GetRepeatDeckUseCase
import com.example.deck.domain.usecase.remote.SetCompletedDeckUseCase
import com.example.deck.presentation.viewmodel.DeckViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeckViewModelFactory @Inject constructor(
    private val getLearnDeckUseCase : GetLearnDeckUseCase,
    private val getRepeatDeckUseCase : GetRepeatDeckUseCase,
    private val setCompletedDeckUseCase : SetCompletedDeckUseCase,
    private val addCompletedWordUseCase : AddCompletedWordUseCase,
    private val shouldSendCompletedDeckUseCase : ShouldSendCompletedDeckUseCase,
    private val getCompletedDeckUseCase : GetCompletedDeckUseCase,
    private val clearCompletedDeckUseCase : ClearCompletedDeckUseCase,
    private val logOutUseCase : LogOutUseCase,
    private val getCurrentUserUseCase : GetCurrentUserUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass : Class<T>) : T {
        if (modelClass.isAssignableFrom(DeckViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return DeckViewModel(
                getLearnDeckUseCase = getLearnDeckUseCase,
                getRepeatDeckUseCase = getRepeatDeckUseCase,
                setCompletedDeckUseCase = setCompletedDeckUseCase,
                addCompletedWordUseCase = addCompletedWordUseCase,
                shouldSendCompletedDeckUseCase = shouldSendCompletedDeckUseCase,
                getCompletedDeckUseCase = getCompletedDeckUseCase,
                clearCompletedDeckUseCase = clearCompletedDeckUseCase,
                logOutUseCase = logOutUseCase,
                getCurrentUserUseCase = getCurrentUserUseCase

            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}