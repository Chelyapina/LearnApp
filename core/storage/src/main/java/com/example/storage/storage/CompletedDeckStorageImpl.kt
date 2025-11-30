package com.example.storage.storage

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import com.example.storage.model.WordCompletedStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompletedDeckStorageImpl @Inject constructor(
    private val context : Context
) : CompletedDeckStorage {

    private val prefs by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    override suspend fun saveCompletedDeck(deck : List<WordCompletedStorage>) =
            withContext(Dispatchers.IO) {
                try {
                    val jsonString = json.encodeToString(deck)
                    prefs.edit { putString(KEY_COMPLETED_DECK, jsonString) }
                } catch (e : Exception) {
                    Log.e(ErrorMessages.TAG, ErrorMessages.SAVE_COMPLETED_DECK_ERROR, e)
                    throw e
                }
            }

    override suspend fun getCompletedDeck() : List<WordCompletedStorage> = withContext(Dispatchers.IO) {
        val jsonString = prefs.getString(KEY_COMPLETED_DECK, "[]") ?: "[]"
        try {
            json.decodeFromString<List<WordCompletedStorage>>(jsonString)
        } catch (e : Exception) {
            Log.e(ErrorMessages.TAG, ErrorMessages.GET_COMPLETED_DECK_ERROR, e)
            emptyList()
        }
    }

    override suspend fun clearCompletedDeck() = withContext(Dispatchers.IO) {
        try {
            prefs.edit { remove(KEY_COMPLETED_DECK) }
        } catch (e : Exception) {
            Log.e(ErrorMessages.TAG, ErrorMessages.CLEAR_COMPLETED_DECK_ERROR, e)
            throw e
        }
    }

    companion object {
        private object ErrorMessages {
            const val TAG = "CompletedDeckStorage"
            const val SAVE_COMPLETED_DECK_ERROR = "Error saving completed deck"
            const val GET_COMPLETED_DECK_ERROR = "Error getting completed deck"
            const val CLEAR_COMPLETED_DECK_ERROR = "Error clearing completed deck"
        }

        private const val PREFS_NAME = "completed_deck"
        private const val KEY_COMPLETED_DECK = "completed_words"
    }
}