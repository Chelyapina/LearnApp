package com.example.statistics.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.statistics.domain.model.MonthlyStat
import com.example.statistics.domain.usecase.GetMonthlyStatisticsUseCase
import com.example.statistics.domain.usecase.GetYearlyStatisticsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StatisticsViewModel(
    private val getYearlyStatisticsUseCase: GetYearlyStatisticsUseCase,
    private val getMonthlyStatisticsUseCase: GetMonthlyStatisticsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<StatisticsUiState>(StatisticsUiState.Loading)
    val uiState: StateFlow<StatisticsUiState> = _uiState.asStateFlow()

    private var currentYear = DEFAULT_YEAR
    private var currentSelectedMonth = MonthMapper.toNumber(DEFAULT_MONTH)

    init {
        loadYearlyStatistics()
    }

    fun loadYearlyStatistics() {
        viewModelScope.launch {
            _uiState.value = StatisticsUiState.Loading

            val yearlyResult = getYearlyStatisticsUseCase(currentYear)

            yearlyResult.fold(
                onSuccess = { yearlyStats ->
                    loadMonthlyStatistics(currentSelectedMonth, yearlyStats)
                },
                onFailure = { exception ->
                    _uiState.value = StatisticsUiState.Error(
                        exception.message ?: ERROR
                    )
                }
            )
        }
    }

    private suspend fun loadMonthlyStatistics(month: Int, yearlyStats: List<MonthlyStat>) {
        val monthlyResult = getMonthlyStatisticsUseCase(currentYear, month)

        monthlyResult.fold(
            onSuccess = { monthlyStats ->
                _uiState.value = StatisticsUiState.Content(
                    currentYear = currentYear,
                    yearlyStats = yearlyStats,
                    selectedMonthName =  MonthMapper.toRussianFullName(MonthMapper.toEnglishName(month)),
                    selectedMonthStats = monthlyStats
                )
            },
            onFailure = { exception ->
                _uiState.value = StatisticsUiState.Content(
                    currentYear = currentYear,
                    yearlyStats = yearlyStats,
                    selectedMonthName = MonthMapper.toRussianFullName(MonthMapper.toEnglishName(month)),
                    selectedMonthStats = emptyList()
                )
            }
        )
    }

    fun onMonthSelected(monthIndex: Int) {
        if (monthIndex == currentSelectedMonth - 1) return

        currentSelectedMonth = monthIndex + 1

        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState is StatisticsUiState.Content) {
                loadMonthlyStatistics(currentSelectedMonth, currentState.yearlyStats)
            }
        }
    }

    fun onYearChange(newYear: Int) {
        if (newYear == currentYear) return
        if (newYear !in AVAILABLE_YEARS) return

        currentYear = newYear
        currentSelectedMonth = MonthMapper.toNumber(DEFAULT_MONTH)
        loadYearlyStatistics()
    }

    fun retry() {
        loadYearlyStatistics()
    }

    companion object {
        private const val DEFAULT_YEAR = 2026
        private const val DEFAULT_MONTH = "May"
        private val AVAILABLE_YEARS = listOf(2025, 2026)
        private const val ERROR = "Ошибка загрузки статистики"
    }
}

