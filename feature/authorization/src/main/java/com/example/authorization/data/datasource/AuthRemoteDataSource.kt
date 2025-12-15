package com.example.authorization.data.datasource

import com.example.network.ApiService
import com.example.network.exception.ExceptionMapper
import com.example.network.modelDto.AuthResponseDto
import com.example.network.modelDto.LoginRequestDto
import javax.inject.Inject

internal class AuthRemoteDataSource @Inject constructor(
    private val apiService : ApiService
) {
    suspend fun login(username : String, password : String) : AuthResponseDto {
        try {
            val request = LoginRequestDto(username, password)
            return apiService.login(request)
        } catch (e : Exception) {
            val networkException = ExceptionMapper.mapToNetworkException(e)
            throw networkException
        }
    }
}