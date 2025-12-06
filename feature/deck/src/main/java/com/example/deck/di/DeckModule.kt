package com.example.deck.di

import com.example.deck.data.repository.DeckRepositoryImpl
import com.example.deck.domain.repository.DeckRepository
import dagger.Binds
import dagger.Module

@Module
internal abstract class DeckModule {

    @Binds
    internal abstract fun bindDeckRepository(impl : DeckRepositoryImpl) : DeckRepository
}