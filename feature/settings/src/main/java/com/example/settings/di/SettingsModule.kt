package com.example.settings.di

import com.example.settings.data.SettingsRepositoryImpl
import com.example.settings.domain.SettingsRepository
import com.example.settings.domain.usecase.GetSettingsUseCase
import com.example.settings.domain.usecase.UpdateSettingsUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [SettingsModule::class])
object PublicSettingsModule {
    @Provides
    fun provideGetSettingsUseCase(repository : SettingsRepository) : GetSettingsUseCase {
        return GetSettingsUseCase(repository)
    }

    @Provides
    fun provideUpdateSettingsUseCase(repository : SettingsRepository) : UpdateSettingsUseCase {
        return UpdateSettingsUseCase(repository)
    }
}

@Module
internal abstract class SettingsModule {
    @Binds
    internal abstract fun bindSettingsRepository(impl : SettingsRepositoryImpl) : SettingsRepository
}