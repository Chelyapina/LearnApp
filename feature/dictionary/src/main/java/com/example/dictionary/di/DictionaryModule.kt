package com.example.dictionary.di

import com.example.dictionary.domain.usecase.AddWordToDictionaryUseCase
import com.example.dictionary.domain.usecase.DeleteDictionaryUseCase
import com.example.dictionary.domain.DictionaryRepository
import com.example.dictionary.presentation.di.DictionaryViewModelFactory
import com.example.dictionary.domain.usecase.GetDictionariesUseCase
import com.example.dictionary.domain.usecase.RemoveWordFromDictionaryUseCase
import com.example.dictionary.domain.usecase.SearchWordsUseCase
import com.example.dictionary.data.DictionaryRepositoryImpl
import com.example.dictionary.domain.usecase.CreateDictionaryUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DictionaryModule.Bindings::class])
object DictionaryModule {

    @Module
    internal interface Bindings {
        @Binds
        fun bindDictionaryRepository(impl : DictionaryRepositoryImpl) : DictionaryRepository
    }

    @Provides
    @Singleton
    fun provideCreateDictionaryUseCase(repository : DictionaryRepository) : CreateDictionaryUseCase {
        return CreateDictionaryUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAddWordToDictionaryUseCase(repository : DictionaryRepository) : AddWordToDictionaryUseCase {
        return AddWordToDictionaryUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideRemoveWordFromDictionaryUseCase(repository : DictionaryRepository) : RemoveWordFromDictionaryUseCase {
        return RemoveWordFromDictionaryUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteDictionaryUseCase(repository : DictionaryRepository) : DeleteDictionaryUseCase {
        return DeleteDictionaryUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSearchWordsUseCase(repository : DictionaryRepository) : SearchWordsUseCase {
        return SearchWordsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetDictionariesUseCase(repository : DictionaryRepository) : GetDictionariesUseCase {
        return GetDictionariesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDictionaryViewModelFactory(
        createDictionaryUseCase : CreateDictionaryUseCase,
        addWordToDictionaryUseCase : AddWordToDictionaryUseCase,
        removeWordFromDictionaryUseCase : RemoveWordFromDictionaryUseCase,
        deleteDictionaryUseCase : DeleteDictionaryUseCase,
        searchWordsUseCase : SearchWordsUseCase,
        getDictionariesUseCase : GetDictionariesUseCase
    ) : DictionaryViewModelFactory {
        return DictionaryViewModelFactory(
            createDictionaryUseCase,
            addWordToDictionaryUseCase,
            removeWordFromDictionaryUseCase,
            deleteDictionaryUseCase,
            searchWordsUseCase,
            getDictionariesUseCase
        )
    }
}