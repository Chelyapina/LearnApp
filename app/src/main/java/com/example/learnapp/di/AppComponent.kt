package com.example.learnapp.di

import com.example.learnapp.MainActivity
import com.example.deck.di.DeckModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DeckModule::class])
interface AppComponent {
    fun inject(activity : MainActivity)
}