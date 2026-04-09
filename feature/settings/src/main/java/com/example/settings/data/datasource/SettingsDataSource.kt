package com.example.settings.data.datasource

import com.example.network.ApiService
import com.example.network.exception.ExceptionMapper
import com.example.network.exception.NetworkException
import com.example.network.modelDto.ErrorResponseDto
import com.example.network.modelDto.SettingsResponseDto
import com.example.network.modelDto.UpdateSettingsRequestDto
import com.example.security.storage.SecureStorage
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

internal class SettingsRemoteDataSource @Inject constructor(
    private val apiService : ApiService, private val secureStorage : SecureStorage
) {

    suspend fun getSettings() : SettingsResponseDto {
        return try {
            val token = getAuthToken()
            val response : Response<SettingsResponseDto> = apiService.getSettings("Bearer $token")

            if (response.isSuccessful && response.body() != null) {
                response.body()!!
            } else {
                throw HttpException(response)
            }
        } catch (e : Exception) {
            throw ExceptionMapper.mapToNetworkException(e)
        }
    }

    suspend fun updateSettings(request : UpdateSettingsRequestDto) : SettingsResponseDto {
        return try {
            val token = getAuthToken()
            val response : Response<Unit> = apiService.updateSettings("Bearer $token", request)

            if (response.isSuccessful) {
                SettingsResponseDto(
                    limitNew = request.limitNew,
                    limitRepeat = request.limitRepeat,
                    name = request.name
                )
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = if (!errorBody.isNullOrBlank()) {
                    try {
                        val json = Json { ignoreUnknownKeys = true }
                        val error = json.decodeFromString<ErrorResponseDto>(errorBody)
                        error.message ?: SAVE_ERROR_MESSAGE
                    } catch (_ : Exception) {
                        SAVE_ERROR_MESSAGE
                    }
                } else {
                    SAVE_ERROR_MESSAGE
                }
                throw Exception(errorMessage)
            }
        } catch (e : Exception) {
            val finalException = if (e is java.io.EOFException) {
                Exception(CONNECTION_ERROR_MESSAGE)
            } else {
                e
            }
            throw ExceptionMapper.mapToNetworkException(finalException)
        }
    }

    private suspend fun getAuthToken() : String {
        return secureStorage.getToken() ?: throw NetworkException.UnauthorizedError(null)
    }

    companion object {
        private const val SAVE_ERROR_MESSAGE = "Ошибка сохранения"
        private const val CONNECTION_ERROR_MESSAGE = "Ошибка соединения с сервером"
    }
}