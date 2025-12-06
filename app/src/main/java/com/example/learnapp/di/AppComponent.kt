package com.example.learnapp.di

import com.example.authorization.di.PublicAuthModule
import com.example.learnapp.MainActivity
import com.example.deck.di.PublicDeckModule
import com.example.security.di.SecurityModule
import com.example.storage.di.StorageModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        SecurityModule::class,
        StorageModule::class,
        AppModule::class,
        PublicDeckModule::class,
        PublicAuthModule::class,
        ViewModelFactoryModule::class
    ]
)
interface AppComponent {
    fun inject(activity : MainActivity)
}