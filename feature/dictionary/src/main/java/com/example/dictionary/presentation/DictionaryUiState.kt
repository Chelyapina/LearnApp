package com.example.dictionary.presentation

import com.example.dictionary.domain.model.Dictionary
import com.example.dictionary.domain.model.SearchWordResult

sealed class DictionaryUiState {
    object Loading : DictionaryUiState()
    data class Error(val message : String) : DictionaryUiState()
    data class Content(
        val dictionaries : List<Dictionary> = emptyList(),
        val searchResults : List<SearchWordResult> = emptyList(),
        val isSearching : Boolean = false
    ) : DictionaryUiState()
}