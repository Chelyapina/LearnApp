package com.example.deck.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.deck.R
import com.example.deck.presentation.model.DeckType
import com.example.deck.presentation.model.WordCardUI
import com.example.designsystem.theme.learnDeckColor
import com.example.designsystem.theme.learnDeckTextColor
import com.example.designsystem.theme.repeatDeckColor
import com.example.designsystem.theme.repeatDeckTextColor

@Composable
fun WordCard(
    word : WordCardUI,
    modifier : Modifier = Modifier,
    onCardClick : () -> Unit,
    isTranslation : Boolean,
    isActive : Boolean = true,
    isTopCard : Boolean = false,
    deckType: DeckType,
) {
    Card(
        onClick = onCardClick, modifier = modifier, elevation = CardDefaults.cardElevation(
            defaultElevation = if (isTopCard) 12.dp else 4.dp
        ), colors = CardDefaults.cardColors(
            containerColor = if (isActive) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.primaryContainer
            }, contentColor = if (isActive) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
            }
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp), contentAlignment = Alignment.Center
        ) {
            if (isActive && isTopCard) {
                DeckLabel(
                    label = when (deckType) {
                        DeckType.LEARN -> stringResource(R.string.deck_label_learn)
                        DeckType.REPEAT -> stringResource(R.string.deck_label_repeat)
                    },
                    deckType = deckType,
                    modifier = Modifier.Companion
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                )
            }

            if (isTranslation) {
                TranslateWord(wordTranslate = word.wordTranslate)
            } else {
                OriginalWord(
                    wordOriginal = word.originalWord, wordTranscription = word.wordTranscription
                )
            }
        }
    }
}

@Composable
private fun DeckLabel(
    label : String,
    deckType: DeckType,
    modifier : Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme
    val (backgroundColor, textColor) = when (deckType) {
        DeckType.LEARN -> colorScheme.learnDeckColor to colorScheme.learnDeckTextColor
        DeckType.REPEAT -> colorScheme.repeatDeckColor to colorScheme.repeatDeckTextColor
    }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor,
        contentColor = textColor,
        tonalElevation = 2.dp
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun TranslateWord(wordTranslate : String) {
    Text(
        text = wordTranslate,
        style = MaterialTheme.typography.displayLarge,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun OriginalWord(
    wordOriginal : String, wordTranscription : String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = wordOriginal,
            style = MaterialTheme.typography.displayLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = wordTranscription,
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
        )
    }
}