package com.example.navigation

interface AppNavigator {
    fun navigateToMain()
    fun navigateToAuth()
    fun popBackStack()
    fun exitApp()
}