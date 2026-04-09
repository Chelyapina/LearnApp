package com.example.settings.presentation.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.settings.domain.usecase.GetSettingsUseCase
import com.example.settings.domain.usecase.UpdateSettingsUseCase
import com.example.settings.presentation.SettingsViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsViewModelFactory @Inject constructor(
    private val getSettingsUseCase : GetSettingsUseCase,
    private val updateSettingsUseCase : UpdateSettingsUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass : Class<T>) : T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(getSettingsUseCase, updateSettingsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}