package com.example.deck.presentation.state

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.deck.R
import com.example.deck.presentation.components.CardDeck
import com.example.deck.presentation.components.EmptyDeck
import com.example.deck.presentation.model.Deck
import com.example.deck.presentation.model.DeckType
import com.example.deck.presentation.viewmodel.DeckViewModel

@Composable
fun ContentState(
    state : DeckScreenState.Content,
    viewModel : DeckViewModel,
    onCardClick : () -> Unit,
    modifier : Modifier = Modifier
) {
    val currentDeck : Deck = when (state.currentDeckType) {
        DeckType.LEARN -> state.learnDeck
        DeckType.REPEAT -> state.repeatDeck
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
                        viewModel.switchDeck(DeckType.entries[index])
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
                    onReset = { viewModel.resetDeck(currentDeck.type) })
            } else {
                CardDeck(
                    wordList = currentDeck.words,
                    currentCardIndex = currentDeck.currentIndex,
                    onCardClick = onCardClick,
                    onSwipeLeft = { currentWord?.let { viewModel.markWordAsForgotten(it.id) } },
                    onSwipeRight = { currentWord?.let { viewModel.markWordAsKnown(it.id) } },
                    deckType = currentDeck.type
                )
            }
        }
    }
}