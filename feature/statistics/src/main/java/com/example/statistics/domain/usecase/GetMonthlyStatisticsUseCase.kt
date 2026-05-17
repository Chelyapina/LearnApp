package com.example.statistics.domain.usecase

import com.example.statistics.domain.StatisticsRepository
import com.example.statistics.domain.model.DailyStat
import javax.inject.Inject

class GetMonthlyStatisticsUseCase @Inject constructor(
    private val repository : StatisticsRepository
) {
    suspend operator fun invoke(year : Int, month : Int) : Result<List<DailyStat>> {
        return repository.getMonthlyStatistics(year, month)
    }
}