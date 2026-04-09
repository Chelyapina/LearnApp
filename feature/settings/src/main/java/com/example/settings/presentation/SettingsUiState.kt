package com.example.settings.presentation

sealed interface SettingsUiState {
    data class Success(
        val limitNewWords : Int = 10,
        val limitWordsForRepeat : Int = 10,
        val newPassword : String = "",
        val confirmPassword : String = "",
        val oldPassword : String = ""
    ) : SettingsUiState

    data object Loading : SettingsUiState
}