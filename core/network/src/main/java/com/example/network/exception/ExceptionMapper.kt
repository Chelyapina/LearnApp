package com.example.network.exception

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
                when (e.code()) {
                    401 -> NetworkException.UnauthorizedError
                    in 400..499 -> NetworkException.ServerError
                    in 500..599 -> NetworkException.ServerError
                    else -> NetworkException.UnknownError
                }
            }

            else -> NetworkException.UnknownError
        }
    }
}