package com.example.learnapp.di

import com.example.learnapp.MainActivity
import com.example.deck.di.DeckModule
import com.example.security.di.SecurityModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [SecurityModule::class, AppModule::class, DeckModule::class])
interface AppComponent {
    fun inject(activity : MainActivity)
}