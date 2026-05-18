package com.example.dictionary.presentation.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dictionary.domain.usecase.AddWordToDictionaryUseCase
import com.example.dictionary.domain.usecase.CreateDictionaryUseCase
import com.example.dictionary.domain.usecase.DeleteDictionaryUseCase
import com.example.dictionary.domain.usecase.GetDictionariesUseCase
import com.example.dictionary.domain.usecase.RemoveWordFromDictionaryUseCase
import com.example.dictionary.domain.usecase.SearchWordsUseCase
import com.example.dictionary.presentation.DictionaryViewModel
import javax.inject.Inject

class DictionaryViewModelFactory @Inject constructor(
    private val createDictionaryUseCase : CreateDictionaryUseCase,
    private val addWordToDictionaryUseCase : AddWordToDictionaryUseCase,
    private val removeWordFromDictionaryUseCase : RemoveWordFromDictionaryUseCase,
    private val deleteDictionaryUseCase : DeleteDictionaryUseCase,
    private val searchWordsUseCase : SearchWordsUseCase,
    private val getDictionariesUseCase : GetDictionariesUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass : Class<T>) : T {
        if (modelClass.isAssignableFrom(DictionaryViewModel::class.java)) {
            return DictionaryViewModel(
                createDictionaryUseCase,
                addWordToDictionaryUseCase,
                removeWordFromDictionaryUseCase,
                deleteDictionaryUseCase,
                searchWordsUseCase,
                getDictionariesUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}