package com.example.deck.presentation.model

data class Deck(
    val type: DeckType,
    val words: List<WordCardUI>,
    val currentIndex: Int = 0,
    val isCompleted: Boolean = false
) {
    val progress: String = "Слово ${currentIndex + 1} из ${words.size}"
    val isEmpty: Boolean get() = words.isEmpty() || isCompleted
    val currentWord: WordCardUI? get() = words.getOrNull(currentIndex)
}

enum class DeckType {
    LEARN, REPEAT
}