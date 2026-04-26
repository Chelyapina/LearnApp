package com.example.models.di

import com.example.models.AuthDataSource
import com.example.models.AuthStateManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ModelsModule {

    @Provides
    @Singleton
    fun provideAuthStateManager(
        authDataSource: AuthDataSource
    ): AuthStateManager {
        return AuthStateManager(authDataSource)
    }
}