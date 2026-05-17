package com.example.statistics.presentation.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.statistics.domain.usecase.GetMonthlyStatisticsUseCase
import com.example.statistics.domain.usecase.GetYearlyStatisticsUseCase
import com.example.statistics.presentation.StatisticsViewModel
import javax.inject.Inject

class StatisticsViewModelFactory @Inject constructor(
    private val getYearlyStatisticsUseCase : GetYearlyStatisticsUseCase,
    private val getMonthlyStatisticsUseCase : GetMonthlyStatisticsUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass : Class<T>) : T {
        if (modelClass.isAssignableFrom(StatisticsViewModel::class.java)) {
            return StatisticsViewModel(
                getYearlyStatisticsUseCase, getMonthlyStatisticsUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}