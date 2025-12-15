package com.example.authorization.domain.usecase

import com.example.authorization.data.utils.AuthConstants
import com.example.authorization.domain.entity.Auth
import com.example.authorization.domain.exception.AuthException
import com.example.designsystem.state.LoadError
import javax.inject.Inject

class ScenarioLogin @Inject constructor(
    private val loginUseCase : LoginUseCase, private val saveUserUseCase : SaveUserUseCase
) {
    sealed interface Result {
        data object Success : Result
        data class Error(val error : LoadError) : Result
    }

    suspend operator fun invoke(credentials : Auth) : Result {
        return try {
            val user = loginUseCase(credentials)
            saveUserUseCase(user)
            Result.Success
        } catch (e : AuthException) {
            Result.Error(mapAuthExceptionToLoadError(e))
        } catch (_ : Exception) {
            Result.Error(LoadError.GenericError)
        }
    }

    private fun mapAuthExceptionToLoadError(exception : AuthException) : LoadError {
        return when (exception.message) {
            AuthConstants.INVALID_CREDENTIALS -> LoadError.Unauthorized
            AuthConstants.NETWORK_ERROR -> LoadError.Network
            AuthConstants.SERVER_ERROR -> LoadError.Server(
                code = 500, message = exception.message ?: DEFAULT_SERVER_ERROR_MESSAGE
            )

            else -> LoadError.GenericError
        }
    }

    companion object {
        private const val DEFAULT_SERVER_ERROR_MESSAGE = "Server error"
    }
}