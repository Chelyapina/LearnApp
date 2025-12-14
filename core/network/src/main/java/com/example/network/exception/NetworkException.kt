package com.example.network.exception

sealed class NetworkException(message : String) : Exception(message) {
    object NetworkError : NetworkException("Network error occurred")
    object TimeoutError : NetworkException("Request timeout")
    object ServerError : NetworkException("Server error")
    object UnauthorizedError : NetworkException("Invalid credentials")
    object UnknownError : NetworkException("Unknown error")
}