package com.example.statistics.data

import com.example.statistics.domain.StatisticsRepository
import com.example.statistics.domain.model.DailyStat
import com.example.statistics.domain.model.MonthlyStat
import javax.inject.Inject

internal class StatisticsRepositoryImpl @Inject constructor(
    private val remoteDataSource : StatisticsRemoteDataSource
) : StatisticsRepository {

    override suspend fun getYearlyStatistics(year : Int) : Result<List<MonthlyStat>> {
        return try {
            val stats = remoteDataSource.getYearlyStatistics(year)
            Result.success(stats)
        } catch (e : Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMonthlyStatistics(year : Int, month : Int) : Result<List<DailyStat>> {
        return try {
            val stats = remoteDataSource.getMonthlyStatistics(year, month)
            Result.success(stats)
        } catch (e : Exception) {
            Result.failure(e)
        }
    }
}