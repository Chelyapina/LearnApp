package com.example.authorization.presentation.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.authorization.domain.usecase.LoginUseCase
import com.example.authorization.domain.usecase.SaveTokenUseCase
import com.example.authorization.presentation.viewmodel.AuthViewModel
import javax.inject.Inject

class AuthViewModelFactory @Inject constructor(
    private val loginUseCase : LoginUseCase, private val saveTokenUseCase : SaveTokenUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass : Class<T>) : T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(loginUseCase, saveTokenUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}