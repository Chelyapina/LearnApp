package com.example.statistics.presentation

import com.example.statistics.domain.model.DailyStat
import com.example.statistics.domain.model.MonthlyStat

sealed class StatisticsUiState {
    object Loading : StatisticsUiState()
    data class Error(val message : String) : StatisticsUiState()
    data class Content(
        val currentYear : Int,
        val yearlyStats : List<MonthlyStat>,
        val selectedMonthName : String,
        val selectedMonthStats : List<DailyStat>
    ) : StatisticsUiState()
}