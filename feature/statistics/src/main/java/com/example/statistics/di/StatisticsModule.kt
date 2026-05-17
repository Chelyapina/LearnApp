package com.example.statistics.di

import com.example.statistics.presentation.di.StatisticsViewModelFactory
import com.example.statistics.data.StatisticsRepositoryImpl
import com.example.statistics.domain.StatisticsRepository
import com.example.statistics.domain.usecase.GetMonthlyStatisticsUseCase
import com.example.statistics.domain.usecase.GetYearlyStatisticsUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [StatisticsModule.Bindings::class])
object StatisticsModule {

    @Module
    internal interface Bindings {
        @Binds
        fun bindStatisticsRepository(impl : StatisticsRepositoryImpl) : StatisticsRepository
    }

    @Provides
    @Singleton
    fun provideGetYearlyStatisticsUseCase(repository : StatisticsRepository) : GetYearlyStatisticsUseCase {
        return GetYearlyStatisticsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetMonthlyStatisticsUseCase(repository : StatisticsRepository) : GetMonthlyStatisticsUseCase {
        return GetMonthlyStatisticsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideStatisticsViewModelFactory(
        getYearlyStatisticsUseCase : GetYearlyStatisticsUseCase,
        getMonthlyStatisticsUseCase : GetMonthlyStatisticsUseCase
    ) : StatisticsViewModelFactory {
        return StatisticsViewModelFactory(
            getYearlyStatisticsUseCase, getMonthlyStatisticsUseCase
        )
    }
}