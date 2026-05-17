package com.example.statistics.domain

import com.example.statistics.domain.model.DailyStat
import com.example.statistics.domain.model.MonthlyStat

interface StatisticsRepository {
    suspend fun getYearlyStatistics(year : Int) : Result<List<MonthlyStat>>
    suspend fun getMonthlyStatistics(year : Int, month : Int) : Result<List<DailyStat>>
}