package com.example.splash.di

import com.example.models.AuthDataSource
import com.example.models.AuthStateManager
import com.example.splash.data.SplashRepository
import com.example.splash.presentation.SplashAuthDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object SplashModule {
    @Provides
    @Singleton
    fun provideAuthDataSource(splashRepository : SplashRepository) : AuthDataSource =
            SplashAuthDataSource(splashRepository)

    @Provides
    @Singleton
    fun provideSplashViewModelFactory(authStateManager : AuthStateManager) : SplashViewModelFactory =
            SplashViewModelFactory(authStateManager)
}