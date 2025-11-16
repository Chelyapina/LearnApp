package com.example.deck.data

import android.util.Log
import com.example.deck.domain.entity.CompletedDeck
import com.example.deck.domain.entity.WordCard
import com.example.deck.domain.entity.WordCompleted
import javax.inject.Inject

class DeckRemoteMockDataSource @Inject constructor() {

    private val learnDeck = listOf(
        WordCard(1, 0, "Hello", "Привет", "/həˈləʊ/"),
        WordCard(2, 0, "World", "Мир", "/wɜːrld/"),
        WordCard(3, 0, "Computer", "Компьютер", "/kəmˈpjuːtər/"),
        WordCard(4, 0, "Language", "Язык", "/ˈlæŋɡwɪdʒ/"),
        WordCard(5, 0, "Water", "Вода", "/ˈwɔːtər/"),
        WordCard(6, 0, "House", "Дом", "/haʊs/"),
        WordCard(7, 0, "Friend", "Друг", "/frend/"),
        WordCard(8, 0, "Time", "Время", "/taɪm/"),
        WordCard(9, 0, "School", "Школа", "/skuːl/"),
        WordCard(10, 0, "Family", "Семья", "/ˈfæməli/")
    )

    private val repeatDeck = listOf(
        WordCard(11, 5, "Apple", "Яблоко", "/ˈæpl/"),
        WordCard(12, 7, "Book", "Книга", "/bʊk/"),
        WordCard(13, 3, "Sun", "Солнце", "/sʌn/"),
        WordCard(14, 4, "Moon", "Луна", "/muːn/"),
        WordCard(15, 6, "Star", "Звезда", "/stɑːr/"),
        WordCard(16, 2, "Tree", "Дерево", "/triː/"),
        WordCard(17, 5, "Flower", "Цветок", "/ˈflaʊər/"),
        WordCard(18, 4, "River", "Река", "/ˈrɪvər/"),
        WordCard(19, 3, "Mountain", "Гора", "/ˈmaʊntən/"),
        WordCard(20, 6, "Ocean", "Океан", "/ˈəʊʃən/")
    )

    private val completedDecks = mutableListOf<CompletedDeck>()

    suspend fun getLearnDeck() : List<WordCard> = learnDeck

    suspend fun getRepeatDeck() : List<WordCard> = repeatDeck

    suspend fun saveCompletedDeck(completedDeck : List<WordCompleted>) : Boolean {
        completedDecks.add(CompletedDeck(completedDeck))

        Log.d("DeckRemoteMockDataSource", "COMPLETED DECK SAVED")
        completedDeck.forEach {
            Log.d("DeckRemoteMockDataSource", "Word ID: ${it.id}, Status: ${it.status}")
        }
        Log.d("DeckRemoteMockDataSource", "Total completed decks: ${completedDecks.size}")

        return true
    }
}