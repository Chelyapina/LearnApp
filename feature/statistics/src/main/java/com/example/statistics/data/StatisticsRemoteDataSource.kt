package com.example.statistics.data

import com.example.network.ApiService
import com.example.network.modelDto.DailyStatDto
import com.example.network.modelDto.YearlyStatDto
import com.example.network.exception.ExceptionMapper
import com.example.network.exception.NetworkException
import com.example.security.storage.SecureStorage
import com.example.statistics.domain.model.DailyStat
import com.example.statistics.domain.model.MonthlyStat
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

internal class StatisticsRemoteDataSource @Inject constructor(
    private val apiService : ApiService,
    private val secureStorage : SecureStorage
) {

    suspend fun getYearlyStatistics(year : Int) : List<MonthlyStat> {
        return try {
            val token = getAuthToken()
            val response : Response<List<YearlyStatDto>> = apiService.getYearlyStatistics(
                token = "Bearer $token", year = year
            )

            if (response.isSuccessful && response.body() != null) {
                response.body()!!.map { dto ->
                    MonthlyStat(
                        label = dto.label, value = dto.value
                    )
                }
            } else {
                throw HttpException(response)
            }
        } catch (e : Exception) {
            throw ExceptionMapper.mapToNetworkException(e)
        }
    }

    suspend fun getMonthlyStatistics(year : Int, month : Int) : List<DailyStat> {
        return try {
            val token = getAuthToken()
            val response : Response<List<DailyStatDto>> = apiService.getMonthlyStatistics(
                token = "Bearer $token", year = year, month = month
            )

            if (response.isSuccessful && response.body() != null) {
                response.body()!!.map { dto ->
                    DailyStat(
                        label = dto.label, value = dto.value
                    )
                }
            } else {
                throw HttpException(response)
            }
        } catch (e : Exception) {
            throw ExceptionMapper.mapToNetworkException(e)
        }
    }

    private suspend fun getAuthToken() : String {
        return secureStorage.getToken() ?: throw NetworkException.UnauthorizedError(null)
    }
}