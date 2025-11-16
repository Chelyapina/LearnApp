package com.example.deck.data

import android.content.Context
import androidx.core.content.edit
import com.example.deck.di.ApplicationContext
import com.example.deck.domain.businesslogic.WordProgressCalculator
import com.example.deck.domain.entity.WordCompleted
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class CompletedDeckLocalDataSource @Inject constructor(
    @ApplicationContext private val context : Context,
    private val wordProgressCalculator : WordProgressCalculator
) {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getCompletedDeck() : List<WordCompleted> {
        val json = prefs.getString(KEY_COMPLETED_WORDS, "[]") ?: "[]"
        return try {
            val type = object : TypeToken<List<WordCompleted>>() {}.type
            Gson().fromJson(json, type) ?: emptyList()
        } catch (_ : Exception) {
            emptyList()
        }
    }

    fun addCompletedWord(wordId : Int, isKnown : Boolean) {
        val currentStatus = getWordStatus(wordId) ?: 0
        val newStatus = wordProgressCalculator.calculateNewStatus(currentStatus, isKnown)

        val current = getCompletedDeck().toMutableList()
        current.removeAll { it.id == wordId }
        current.add(WordCompleted(wordId, newStatus))
        saveCompletedDeck(current)
    }

    fun clearCompletedDeck() {
        prefs.edit { remove(KEY_COMPLETED_WORDS) }
    }

    fun getWordStatus(wordId : Int) : Int? {
        return getCompletedDeck().find { it.id == wordId }?.status
    }

    private fun saveCompletedDeck(deck : List<WordCompleted>) {
        val json = Gson().toJson(deck)
        prefs.edit { putString(KEY_COMPLETED_WORDS, json) }
    }

    fun hasReachedLimit() : Boolean = getCompletedDeck().size >= LIMIT

    companion object {
        private const val PREFS_NAME = "completed_deck"
        private const val KEY_COMPLETED_WORDS = "completed_words"
        private const val LIMIT = 10
    }
}