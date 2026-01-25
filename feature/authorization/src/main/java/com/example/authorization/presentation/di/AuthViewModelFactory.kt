package com.example.authorization.presentation.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.authorization.domain.usecase.ScenarioLoginUseCase
import com.example.authorization.presentation.viewmodel.AuthViewModel
import javax.inject.Inject

class AuthViewModelFactory @Inject constructor(
    private val scenarioLoginUseCase : ScenarioLoginUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass : Class<T>) : T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(scenarioLoginUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}