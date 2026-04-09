package com.example.network.exception

import com.example.network.modelDto.ErrorResponseDto
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ExceptionMapper {

    fun mapToNetworkException(e : Exception) : NetworkException {
        return when (e) {
            is SocketTimeoutException -> NetworkException.TimeoutError
            is ConnectException -> NetworkException.NetworkError
            is UnknownHostException -> NetworkException.NetworkError
            is HttpException -> {
                val errorBody = e.response()?.errorBody()?.string()
                val errorMessage = try {
                    val json = Json { ignoreUnknownKeys = true }
                    val error = json.decodeFromString<ErrorResponseDto>(errorBody ?: "")
                    error.message ?: e.message()
                } catch (_ : Exception) {
                    e.message()
                }

                when (e.code()) {
                    401 -> NetworkException.UnauthorizedError(errorMessage)
                    in 400..499 -> NetworkException.ServerError(errorMessage)
                    in 500..599 -> NetworkException.ServerError(errorMessage)
                    else -> NetworkException.UnknownError(errorMessage)
                }
            }

            else -> NetworkException.UnknownError(e.message)
        }
    }
}