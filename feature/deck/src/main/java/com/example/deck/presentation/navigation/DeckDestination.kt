package com.example.deck.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.deck.presentation.screen.DeckScreen
import com.example.deck.presentation.state.DeckNavigationEvent
import com.example.deck.presentation.viewmodel.DeckViewModel

@Composable
fun DeckDestination(
    viewModelFactory: ViewModelProvider.Factory,
    navigation: DeckNavigation
) {
    val viewModel: DeckViewModel = viewModel(factory = viewModelFactory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is DeckNavigationEvent.NavigateToAuth -> navigation.navigateToAuth()
                is DeckNavigationEvent.NavigateBack -> navigation.exitApp()
            }
        }
    }

    DeckScreen(
        viewModel = viewModel,
        uiState = uiState
    )
}