package com.example.splash.di

import com.example.splash.data.SplashRepository
import com.example.splash.presentation.SplashViewModel
import javax.inject.Inject

class SplashViewModelFactory @Inject constructor(
    private val repository : SplashRepository
) {
    fun create() : SplashViewModel {
        return SplashViewModel(repository)
    }
}