package com.example.settings.data

import com.example.network.modelDto.SettingsResponseDto
import com.example.network.modelDto.UpdateSettingsRequestDto
import com.example.settings.data.datasource.SettingsRemoteDataSource
import com.example.settings.domain.SettingsRepository
import com.example.settings.domain.model.Settings
import com.example.settings.domain.model.UpdateSettingsRequest
import javax.inject.Inject

internal class SettingsRepositoryImpl @Inject constructor(
    private val dataSource : SettingsRemoteDataSource
) : SettingsRepository {

    override suspend fun getSettings() : Result<Settings> {
        return try {
            val response = dataSource.getSettings()
            Result.success(response.toDomain())
        } catch (e : Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateSettings(request : UpdateSettingsRequest) : Result<Settings> {
        return try {
            val dto = UpdateSettingsRequestDto(
                limitNew = request.limitNewWords,
                limitRepeat = request.limitWordsForRepeat,
                password = request.oldPassword.takeIf { it.isNotBlank() },
                newPassword = request.newPassword?.takeIf { it.isNotBlank() })
            val response = dataSource.updateSettings(dto)
            Result.success(response.toDomain())
        } catch (e : Exception) {
            Result.failure(e)
        }
    }
}

private fun SettingsResponseDto.toDomain() : Settings {
    return Settings(
        limitNewWords = this.limitNew ?: 10, limitWordsForRepeat = this.limitRepeat ?: 10
    )
}