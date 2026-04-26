package com.example.settings.domain.usecase

import com.example.settings.domain.SettingsRepository
import com.example.settings.domain.model.Settings
import com.example.settings.domain.model.UpdateSettingsRequest
import javax.inject.Inject

class UpdateSettingsUseCase @Inject constructor(
    private val repository : SettingsRepository
) {
    suspend operator fun invoke(
        oldPassword : String,
        limitNewWords : Int? = null,
        limitWordsForRepeat : Int? = null,
        newPassword : String? = null
    ) : Result<Settings> {
        val request = UpdateSettingsRequest(
            limitNewWords = limitNewWords,
            limitWordsForRepeat = limitWordsForRepeat,
            oldPassword = oldPassword,
            newPassword = newPassword
        )
        return repository.updateSettings(request)
    }
}