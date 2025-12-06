package com.example.authorization.di

import com.example.authorization.data.AuthRepositoryImpl
import com.example.authorization.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal abstract class AuthModule {

    @Binds
    @Singleton
    internal abstract fun bindAuthRepository(impl : AuthRepositoryImpl) : AuthRepository
}