package com.example.dictionary.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.domain.usecase.AddWordToDictionaryUseCase
import com.example.dictionary.domain.usecase.CreateDictionaryUseCase
import com.example.dictionary.domain.usecase.DeleteDictionaryUseCase
import com.example.dictionary.domain.usecase.GetDictionariesUseCase
import com.example.dictionary.domain.usecase.RemoveWordFromDictionaryUseCase
import com.example.dictionary.domain.usecase.SearchWordsUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class DictionaryViewModel @Inject constructor(
    private val createDictionaryUseCase : CreateDictionaryUseCase,
    private val addWordToDictionaryUseCase : AddWordToDictionaryUseCase,
    private val removeWordFromDictionaryUseCase : RemoveWordFromDictionaryUseCase,
    private val deleteDictionaryUseCase : DeleteDictionaryUseCase,
    private val searchWordsUseCase : SearchWordsUseCase,
    private val getDictionariesUseCase : GetDictionariesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<DictionaryUiState>(DictionaryUiState.Loading)
    val uiState : StateFlow<DictionaryUiState> = _uiState.asStateFlow()

    private val _errorChannel = MutableSharedFlow<String>()
    val errorChannel : SharedFlow<String> = _errorChannel.asSharedFlow()

    init {
        loadDictionaries()
    }

    fun loadDictionaries() {
        viewModelScope.launch {
            _uiState.value = DictionaryUiState.Loading
            val result = getDictionariesUseCase()

            result.fold(onSuccess = { dictionaries ->
                _uiState.value = DictionaryUiState.Content(dictionaries = dictionaries)
            }, onFailure = { exception ->
                _uiState.value = DictionaryUiState.Error(
                    exception.message ?: ERROR_LOAD_DICTIONARIES
                )
            })
        }
    }

    fun createDictionary(name : String, description : String?, language : String) {
        viewModelScope.launch {
            val result = createDictionaryUseCase(name, description, language)
            result.fold(onSuccess = { loadDictionaries() }, onFailure = { exception ->
                _errorChannel.emit(exception.message ?: ERROR_CREATE_DICTIONARY)
            })
        }
    }

    fun addWordToDictionary(wordId : Int, dictionaryId : Int) {
        viewModelScope.launch {
            val result = addWordToDictionaryUseCase(wordId, dictionaryId)
            result.fold(onSuccess = { loadDictionaries() }, onFailure = { exception ->
                _errorChannel.emit(exception.message ?: ERROR_ADD_WORD)
            })
        }
    }

    fun removeWordFromDictionary(dictionaryId : Int, wordId : Int) {
        viewModelScope.launch {
            val result = removeWordFromDictionaryUseCase(dictionaryId, wordId)
            result.fold(onSuccess = { loadDictionaries() }, onFailure = { exception ->
                _errorChannel.emit(exception.message ?: ERROR_REMOVE_WORD)
            })
        }
    }

    fun deleteDictionary(dictionaryId : Int) {
        viewModelScope.launch {
            val result = deleteDictionaryUseCase(dictionaryId)
            result.fold(onSuccess = { loadDictionaries() }, onFailure = { exception ->
                _errorChannel.emit(exception.message ?: ERROR_DELETE_DICTIONARY)
            })
        }
    }

    fun searchWords(prefix : String) {
        if (prefix.length < 2) {
            _uiState.update { currentState ->
                if (currentState is DictionaryUiState.Content) {
                    currentState.copy(searchResults = emptyList(), isSearching = false)
                } else currentState
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { currentState ->
                if (currentState is DictionaryUiState.Content) {
                    currentState.copy(isSearching = true)
                } else currentState
            }

            val result = searchWordsUseCase(prefix)

            _uiState.update { currentState ->
                if (currentState is DictionaryUiState.Content) {
                    result.fold(onSuccess = { searchResults ->
                        currentState.copy(searchResults = searchResults, isSearching = false)
                    }, onFailure = {
                        currentState.copy(searchResults = emptyList(), isSearching = false)
                    })
                } else currentState
            }
        }
    }

    fun retry() {
        loadDictionaries()
    }

    companion object {
        private const val ERROR_LOAD_DICTIONARIES = "Не удалось загрузить словари"
        private const val ERROR_CREATE_DICTIONARY = "Не удалось создать словарь"
        private const val ERROR_ADD_WORD = "Не удалось добавить слово"
        private const val ERROR_REMOVE_WORD = "Не удалось удалить слово"
        private const val ERROR_DELETE_DICTIONARY = "Не удалось удалить словарь"
    }
}