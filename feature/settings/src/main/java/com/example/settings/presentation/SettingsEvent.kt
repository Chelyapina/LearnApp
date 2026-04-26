package com.example.settings.presentation

sealed class SettingsEvent {
    data class LimitNewWordsChanged(val value : Int) : SettingsEvent()
    data class LimitWordsForRepeatChanged(val value : Int) : SettingsEvent()
    data class NewPasswordChanged(val value : String) : SettingsEvent()
    data class ConfirmPasswordChanged(val value : String) : SettingsEvent()
    data class OldPasswordChanged(val value : String) : SettingsEvent()
    data object SaveSettings : SettingsEvent()
    data object AlertHandled : SettingsEvent()
}