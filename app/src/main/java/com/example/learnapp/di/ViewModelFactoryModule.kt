package com.example.learnapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.authorization.presentation.di.AuthViewModelFactory
import com.example.authorization.presentation.viewmodel.AuthViewModel
import com.example.deck.presentation.di.DeckViewModelFactory
import com.example.deck.presentation.viewmodel.DeckViewModel
import com.example.dictionary.presentation.DictionaryViewModel
import com.example.dictionary.presentation.di.DictionaryViewModelFactory
import com.example.settings.presentation.SettingsViewModel
import com.example.settings.presentation.di.SettingsViewModelFactory
import com.example.splash.di.SplashViewModelFactory
import com.example.splash.presentation.SplashViewModel
import com.example.statistics.presentation.StatisticsViewModel
import com.example.statistics.presentation.di.StatisticsViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ViewModelFactoryModule {

    @Provides
    @Singleton
    fun provideViewModelFactory(
        authViewModelFactory : AuthViewModelFactory,
        deckViewModelFactory : DeckViewModelFactory,
        settingsViewModelFactory : SettingsViewModelFactory,
        splashViewModelFactory : SplashViewModelFactory,
        statisticsViewModelFactory: StatisticsViewModelFactory,
        dictionaryViewModelFactory: DictionaryViewModelFactory
    ) : ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass : Class<T>) : T {
                return when {
                    modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                        authViewModelFactory.create(modelClass)
                    }

                    modelClass.isAssignableFrom(DeckViewModel::class.java) -> {
                        deckViewModelFactory.create(modelClass)
                    }

                    modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                        settingsViewModelFactory.create(modelClass)
                    }

                    modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                        splashViewModelFactory.create()
                    }

                    modelClass.isAssignableFrom(StatisticsViewModel::class.java) -> {
                        statisticsViewModelFactory.create(modelClass)
                    }

                    modelClass.isAssignableFrom(DictionaryViewModel::class.java) -> {
                        dictionaryViewModelFactory.create(modelClass)
                    }

                    else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                } as T
            }
        }
    }
}