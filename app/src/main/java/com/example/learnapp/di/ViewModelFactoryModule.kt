package com.example.learnapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.authorization.presentation.di.AuthViewModelFactory
import com.example.authorization.presentation.viewmodel.AuthViewModel
import com.example.deck.presentation.di.DeckViewModelFactory
import com.example.deck.presentation.viewmodel.DeckViewModel
import com.example.splash.di.SplashViewModelFactory
import com.example.splash.presentation.SplashViewModel
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
        splashViewModelFactory : SplashViewModelFactory
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

                    modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                        splashViewModelFactory.create()
                    }

                    else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                } as T
            }
        }
    }
}