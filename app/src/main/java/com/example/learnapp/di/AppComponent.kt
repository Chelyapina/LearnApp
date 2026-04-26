package com.example.learnapp.di

import com.example.authorization.di.PublicAuthModule
import com.example.learnapp.MainActivity
import com.example.deck.di.PublicDeckModule
import com.example.models.di.ModelsModule
import com.example.network.di.NetworkModule
import com.example.security.di.SecurityModule
import com.example.settings.di.PublicSettingsModule
import com.example.splash.di.SplashModule
import com.example.storage.di.StorageModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ModelsModule::class,
        NetworkModule::class,
        PublicAuthModule::class,
        PublicDeckModule::class,
        SecurityModule::class,
        SplashModule::class,
        StorageModule::class,
        PublicSettingsModule::class,
        ViewModelFactoryModule::class
    ]
)
interface AppComponent {
    fun inject(activity : MainActivity)
}