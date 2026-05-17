package com.example.statistics.domain.usecase

import com.example.statistics.domain.StatisticsRepository
import com.example.statistics.domain.model.MonthlyStat
import javax.inject.Inject

class GetYearlyStatisticsUseCase @Inject constructor(
    private val repository : StatisticsRepository
) {
    suspend operator fun invoke(year : Int) : Result<List<MonthlyStat>> {
        return repository.getYearlyStatistics(year)
    }
}