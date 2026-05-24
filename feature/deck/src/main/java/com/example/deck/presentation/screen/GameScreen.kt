package com.example.deck.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.deck.presentation.viewmodel.DeckViewModel
import com.example.designsystem.components.appbar.AppBarState
import com.example.designsystem.components.appbar.CommonAppBar

@Composable
fun GameScreen(
    viewModel: DeckViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val gameResult by viewModel.gameResult.collectAsStateWithLifecycle()

    val words = uiState.repeatDeck.words

    var currentIndex by remember { mutableStateOf(0) }
    var answerText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CommonAppBar(
                modifier = Modifier.padding(16.dp),
                state = AppBarState.Back(title = "Тренировка слов"),
                onBackClick = {
                    viewModel.clearGameResult()
                    onBackClick()
                }
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            if (words.isEmpty()) {
                Text(text = "У вас нет слов для повторения", fontSize = 20.sp)
            } else if (currentIndex >= words.size) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Отличная работа!", fontSize = 26.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "Вы повторили все доступные слова.", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(onClick = {
                        viewModel.clearGameResult()
                        onBackClick()
                    }) {
                        Text("Вернуться в профиль")
                    }
                }
            } else {
                val currentWord = words[currentIndex]

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Как переводится слово:",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = currentWord.originalWord,
                        fontSize = 36.sp,
                        style = MaterialTheme.typography.headlineLarge
                    )
                    if (currentWord.wordTranscription.isNotEmpty()) {
                        Text(
                            text = "[${currentWord.wordTranscription}]",
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    OutlinedTextField(
                        value = answerText,
                        onValueChange = { if (gameResult == null) answerText = it },
                        label = { Text("Введите перевод") },
                        modifier = Modifier.fillMaxWidth(0.9f),
                        singleLine = true,
                        enabled = gameResult == null
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    if (gameResult != null) {
                        Text(
                            text = gameResult!!,
                            fontSize = 18.sp,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            onClick = {
                                currentIndex++
                                answerText = ""
                                viewModel.clearGameResult()
                            },
                            modifier = Modifier.fillMaxWidth(0.7f)
                        ) {
                            Text("Следующее слово")
                        }
                    } else {
                        Button(
                            onClick = {
                                if (answerText.isNotBlank()) {
                                    viewModel.checkWordAnswer(currentWord.id, answerText)
                                }
                            },
                            modifier = Modifier.fillMaxWidth(0.7f),
                            enabled = answerText.isNotBlank()
                        ) {
                            Text("Проверить")
                        }
                    }
                }
            }
        }
    }
}