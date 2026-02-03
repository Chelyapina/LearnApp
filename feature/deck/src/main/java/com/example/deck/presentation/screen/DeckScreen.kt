package com.example.deck.presentation.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.deck.R
import com.example.deck.presentation.state.DeckEvent
import com.example.deck.presentation.state.DeckScreen
import com.example.deck.presentation.state.DeckUiState
import com.example.deck.presentation.viewmodel.DeckViewModel
import com.example.designsystem.components.appbar.AppBarState
import com.example.designsystem.components.appbar.CommonAppBar
import com.example.designsystem.components.loading.LoadingScreen
import com.example.designsystem.state.LoadingState

@Composable
fun DeckScreen(
    viewModel: DeckViewModel,
    uiState: DeckUiState
) {
    BackHandler(enabled = true) {
        viewModel.handleEvent(DeckEvent.Logout)
    }

    DeckScreenContent(
        viewModel = viewModel,
        uiState = uiState
    )

    when (uiState.isLoading) {
        is LoadingState.Loading -> {
            LoadingScreen(
                modifier = Modifier.fillMaxSize(),
                text = R.string.load_word
            )
        }
        else -> {}
    }

    uiState.alertData?.let { alertData ->
        AlertDialog(
            onDismissRequest = { viewModel.handleEvent(DeckEvent.AlertHandled) },
            title = { Text(text = alertData.title) },
            text = { Text(text = alertData.message) },
            confirmButton = {
                Button(onClick = alertData.onConfirm) {
                    Text(text = alertData.confirmText)
                }
            },
            dismissButton = null
        )
    }
}

@Composable
fun DeckScreenContent(
    modifier: Modifier = Modifier,
    viewModel: DeckViewModel,
    uiState: DeckUiState
) {
    val userName = uiState.user?.name ?: ""

    Scaffold(
        topBar = {
            CommonAppBar(
                modifier = Modifier.padding(16.dp),
                state = AppBarState.TwoActions(
                    firstName = userName,
                    onMenuClick = {},
                    onAvatarClick = {},
                )
            )
        },
        modifier = modifier.systemBarsPadding()
    ) { paddingValues ->
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(paddingValues)
        ) {
            when (val screen = uiState.screen) {
                is DeckScreen.Loading -> {
                }

                is DeckScreen.Content -> {
                    ContentDeckScreen(
                        state = screen,
                        viewModel = viewModel,
                        onCardClick = {},
                        onLogoutClick = { viewModel.handleEvent(DeckEvent.Logout) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                is DeckScreen.Error -> {
                    ErrorDeckScreen(
                        errorMessage = screen.message,
                        onRetry = { viewModel.handleEvent(DeckEvent.LoadDecks) },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}