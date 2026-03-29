package com.example.deck.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.deck.presentation.screen.DeckScreen
import com.example.deck.presentation.state.DeckNavigationEvent
import com.example.deck.presentation.viewmodel.DeckViewModel

@Composable
fun DeckDestination(
    viewModel: DeckViewModel,
    navigation: DeckNavigation
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is DeckNavigationEvent.NavigateToAuth -> navigation.navigateToAuth()
                is DeckNavigationEvent.NavigateBack -> navigation.exitApp()
                is DeckNavigationEvent.NavigateToProfile -> navigation.navigateToProfile()
            }
        }
    }

    DeckScreen(
        viewModel = viewModel,
        uiState = uiState
    )
}