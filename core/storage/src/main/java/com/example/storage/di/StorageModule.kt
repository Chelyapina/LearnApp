package com.example.storage.di

import com.example.storage.storage.CompletedDeckStorage
import com.example.storage.storage.CompletedDeckStorageImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class StorageModule {

    @Binds
    @Singleton
    abstract fun bindCompletedDeckStorage(completedDeckStorageImpl : CompletedDeckStorageImpl) : CompletedDeckStorage
}