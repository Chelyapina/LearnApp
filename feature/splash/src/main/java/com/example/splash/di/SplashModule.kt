package com.example.splash.di

import com.example.splash.data.SplashRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object SplashModule {
    @Provides
    @Singleton
    fun provideSplashViewModelFactory(
        repository : SplashRepository
    ) : SplashViewModelFactory {
        return SplashViewModelFactory(repository)
    }
}