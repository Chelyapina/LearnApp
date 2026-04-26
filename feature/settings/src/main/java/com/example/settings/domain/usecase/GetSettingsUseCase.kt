package com.example.settings.domain.usecase

import com.example.settings.domain.SettingsRepository
import com.example.settings.domain.model.Settings
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    private val repository : SettingsRepository
) {
    suspend operator fun invoke() : Result<Settings> = repository.getSettings()
}