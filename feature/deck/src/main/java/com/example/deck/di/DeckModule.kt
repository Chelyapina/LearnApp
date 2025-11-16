package com.example.deck.di

import com.example.deck.data.DeckRepositoryImpl
import com.example.deck.domain.repository.DeckRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DeckModule {

    @Binds
    abstract fun bindDeckRepository(impl : DeckRepositoryImpl) : DeckRepository
}

