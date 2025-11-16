package com.example.deck.presentation.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.deck.presentation.state.ContentState
import com.example.deck.presentation.state.DeckScreenState
import com.example.deck.presentation.state.ErrorState
import com.example.deck.presentation.state.LoadingState
import com.example.deck.presentation.viewmodel.DeckViewModel
import com.example.designsystem.components.appbar.AppBarState
import com.example.designsystem.components.appbar.CommonAppBar

@Composable
fun DeckScreen(
    viewModel : DeckViewModel
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            CommonAppBar(
                modifier = Modifier.padding(16.dp), state = AppBarState.TwoActions(
                    firstName = "",
                    onMenuClick = {},
                    onAvatarClick = {},
                )
            )
        }, modifier = Modifier.systemBarsPadding()
    ) { paddingValues ->
        when (state) {
            is DeckScreenState.Loading -> LoadingState()
            is DeckScreenState.Content -> ContentState(
                state = state as DeckScreenState.Content,
                viewModel = viewModel,
                onCardClick = {},
                modifier = Modifier.padding(paddingValues)
            )

            is DeckScreenState.Error -> ErrorState(
                errorMessage = "", onRetry = {})
        }
    }
}