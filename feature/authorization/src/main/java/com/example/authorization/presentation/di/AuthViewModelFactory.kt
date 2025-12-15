package com.example.authorization.presentation.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.authorization.domain.usecase.ScenarioLogin
import com.example.authorization.presentation.viewmodel.AuthViewModel
import javax.inject.Inject

class AuthViewModelFactory @Inject constructor(
    private val scenarioLogin : ScenarioLogin
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass : Class<T>) : T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(scenarioLogin) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}