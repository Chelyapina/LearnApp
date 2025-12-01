package com.example.learnapp.di

import com.example.authorization.di.PublicAuthModule
import com.example.deck.di.DeckModule
import com.example.learnapp.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DeckModule::class, PublicAuthModule::class, ViewModelFactoryModule::class])
interface AppComponent {
    fun inject(activity : MainActivity)
}