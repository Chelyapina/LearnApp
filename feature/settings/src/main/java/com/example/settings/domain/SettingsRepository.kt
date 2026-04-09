package com.example.settings.domain

import com.example.settings.domain.model.Settings
import com.example.settings.domain.model.UpdateSettingsRequest

interface SettingsRepository {
    suspend fun getSettings() : Result<Settings>
    suspend fun updateSettings(request : UpdateSettingsRequest) : Result<Settings>
}