package com.example.network.exception

sealed class NetworkException(message: String?) : Exception(message ?: "Unknown error") {
    object NetworkError : NetworkException("Network error occurred")
    object TimeoutError : NetworkException("Request timeout")
    data class ServerError(val errorMessage: String?) : NetworkException(errorMessage ?: "Server error")
    data class UnauthorizedError(val errorMessage: String?) : NetworkException(errorMessage ?: "Invalid credentials")
    data class UnknownError(val errorMessage: String?) : NetworkException(errorMessage ?: "Unknown error")
}