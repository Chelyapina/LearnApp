package com.example.security.di

import com.example.security.storage.SecureStorage
import com.example.security.storage.SecureStorageImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class SecurityModule {

    @Binds
    @Singleton
    abstract fun bindSecureStorage(secureStorageImpl: SecureStorageImpl): SecureStorage
}