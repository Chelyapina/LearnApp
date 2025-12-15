package com.example.deck.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.deck.R
import com.example.deck.presentation.components.CardDeck
import com.example.deck.presentation.components.EmptyDeck
import com.example.deck.presentation.model.DeckType
import com.example.deck.presentation.state.DeckEvent
import com.example.deck.presentation.state.DeckScreen
import com.example.deck.presentation.viewmodel.DeckViewModel

@Composable
fun ContentDeckScreen(
    state : DeckScreen.Content,
    viewModel : DeckViewModel,
    onCardClick : () -> Unit,
    onLogoutClick : () -> Unit,
    modifier : Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    val currentDeck = when (state.currentDeckType) {
        DeckType.LEARN -> uiState.learnDeck
        DeckType.REPEAT -> uiState.repeatDeck
    }

    val currentWord = currentDeck.currentWord

    Column(
        modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TabRow(
            selectedTabIndex = state.currentDeckType.ordinal,
            modifier = Modifier.fillMaxWidth(),
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            listOf(
                stringResource(R.string.tab_row_label_new),
                stringResource(R.string.tab_row_label_repeat)
            ).forEachIndexed { index, title ->
                Tab(
                    text = {
                        Text(
                            text = title, style = MaterialTheme.typography.titleSmall
                        )
                    },
                    selected = state.currentDeckType.ordinal == index,
                    onClick = {
                        viewModel.handleEvent(DeckEvent.SwitchDeck(DeckType.entries[index]))
                    },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (!currentDeck.isEmpty) {
            Text(
                text = currentDeck.progress,
                style = MaterialTheme.typography.displaySmall,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            if (currentDeck.isEmpty) {
                EmptyDeck(
                    onReset = {
                        viewModel.handleEvent(DeckEvent.ResetDeck(currentDeck.type))
                    })
            } else {
                CardDeck(
                    wordList = currentDeck.words,
                    currentCardIndex = currentDeck.currentIndex,
                    onCardClick = onCardClick,  onSwipeLeft = {
                        currentWord?.let {
                            viewModel.handleEvent(DeckEvent.MarkWord(wordId = it.id, isKnown = false))
                        }
                    },
                    onSwipeRight = {
                        currentWord?.let {
                            viewModel.handleEvent(DeckEvent.MarkWord(wordId = it.id, isKnown = true))
                        }
                    },
                    deckType = currentDeck.type
                )
            }
        }

        Button(
            onClick = onLogoutClick,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.8f),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.onErrorContainer
            )
        ) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Выйти")
        }
    }
}