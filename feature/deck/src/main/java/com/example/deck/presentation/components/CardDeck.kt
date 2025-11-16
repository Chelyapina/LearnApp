package com.example.deck.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.compose.ui.zIndex
import com.example.deck.presentation.model.DeckType
import com.example.deck.presentation.model.WordCardUI

@Composable
fun CardDeck(
    wordList : List<WordCardUI>,
    currentCardIndex : Int,
    onCardClick : () -> Unit,
    onSwipeLeft : () -> Unit,
    onSwipeRight : () -> Unit,
    deckType : DeckType,
    modifier : Modifier = Modifier
) {
    val cardsToShowCount = minOf(5, wordList.size - currentCardIndex)
    val cardsToShow = wordList.subList(
        currentCardIndex, currentCardIndex + cardsToShowCount
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(500.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            cardsToShow.forEachIndexed { index, word ->
                val xOffset = index * 25.dp
                val yOffset = index * 8.dp

                StackedCard(
                    word = word,
                    isActive = index == 0,
                    isTopCard = index == 0,
                    onCardClick = onCardClick,
                    deckType = deckType,
                    modifier = Modifier
                        .align(if (index == 0) Alignment.Center else Alignment.TopStart)
                        .offset(
                            x = if (index == 0) 0.dp else xOffset,
                            y = if (index == 0) 0.dp else yOffset
                        )
                        .width(280.dp)
                        .zIndex((cardsToShow.size - index).toFloat())
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        if (cardsToShow.isNotEmpty()) {
            CardActionsRow(
                onForget = onSwipeLeft,
                onKnow = onSwipeRight,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}

@Composable
private fun StackedCard(
    word : WordCardUI,
    isActive : Boolean,
    isTopCard : Boolean,
    onCardClick : () -> Unit,
    deckType : DeckType,
    modifier : Modifier = Modifier
) {
    var isTranslation by remember { mutableStateOf(false) }

    WordCard(
        word = word,
        modifier = modifier
            .height(220.dp)
            .width(280.dp),
        onCardClick = {
            if (isActive) {
                isTranslation = !isTranslation
                onCardClick()
            }
        },
        isTranslation = isTranslation,
        isActive = isActive,
        isTopCard = isTopCard,
        deckType = deckType
    )
}