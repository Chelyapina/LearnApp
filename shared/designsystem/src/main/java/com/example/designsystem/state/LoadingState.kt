package com.example.designsystem.state

sealed class LoadingState {
    object Idle : LoadingState()
    object Loading : LoadingState()
    data class Error(val error : LoadError) : LoadingState()
    object Success : LoadingState()
}

sealed class LoadError {
    data class Validation(val message : String) : LoadError()

    object Network : LoadError()
    data class Server(val code : Int, val message : String) : LoadError()
    object Timeout : LoadError()
    object Unauthorized : LoadError()
    object GenericError : LoadError()

    fun requiresAlert() : Boolean = when (this) {
        is Validation -> false
        else -> true
    }

    fun toAlertConfig() : AlertConfig = when (this) {
        is Network -> AlertConfig.NetworkError
        is Server -> AlertConfig.ServerError(this.code)
        is Timeout -> AlertConfig.TimeoutError
        is Unauthorized -> AlertConfig.UnauthorizedError
        is GenericError -> AlertConfig.GenericError
        is Validation -> AlertConfig.GenericError
    }
}