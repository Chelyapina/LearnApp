package com.example.authorization.data

import com.example.authorization.data.converter.TokenConverter
import com.example.authorization.data.datasource.AuthLocalDataSource
import com.example.authorization.data.datasource.AuthRemoteDataSource
import com.example.authorization.data.utils.AuthConstants
import com.example.authorization.domain.entity.Auth
import com.example.authorization.domain.entity.Token
import com.example.authorization.domain.entity.User
import com.example.authorization.domain.exception.AuthException
import com.example.authorization.domain.repository.AuthRepository
import com.example.network.exception.NetworkException
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource : AuthRemoteDataSource,
    private val localDataSource : AuthLocalDataSource
) : AuthRepository {

    override suspend fun login(credentials : Auth) : User {
        try {
            val response = remoteDataSource.login(
                username = credentials.login, password = credentials.password
            )

            val user = TokenConverter.convertToDomain(response)
            return user

        } catch (e : NetworkException) {
            when (e) {
                is NetworkException.UnauthorizedError -> {
                    throw AuthException(AuthConstants.INVALID_CREDENTIALS)
                }

                is NetworkException.NetworkError -> {
                    throw AuthException(AuthConstants.NETWORK_ERROR)
                }

                is NetworkException.TimeoutError -> {
                    throw AuthException(AuthConstants.TIMEOUT_ERROR)
                }

                else -> {
                    throw AuthException(AuthConstants.SERVER_ERROR)
                }
            }
        } catch (_ : Exception) {
            throw AuthException(AuthConstants.UNKNOWN_ERROR)
        }
    }

    override suspend fun saveUser(user : User) {
        try {
            localDataSource.saveUserData(user)
        } catch (_ : Exception) {
            throw AuthException(AuthConstants.TOKEN_SAVE_ERROR)
        }
    }

    override suspend fun getToken() : Token? {
        return try {
            val tokenValue = localDataSource.getToken()
            tokenValue?.let { Token(it) }
        } catch (_ : Exception) {
            throw AuthException(AuthConstants.TOKEN_READ_ERROR)
        }
    }
}