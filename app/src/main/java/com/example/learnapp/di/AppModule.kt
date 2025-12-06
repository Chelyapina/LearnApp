package com.example.learnapp.di

import android.app.Application
import android.content.Context
import com.example.di.qualifiers.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    @ApplicationContext
    fun provideApplicationContext() : Context = application.applicationContext
}