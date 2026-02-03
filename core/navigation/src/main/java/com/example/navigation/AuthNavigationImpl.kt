package com.example.navigation

import androidx.navigation.NavController
import com.example.authorization.presentation.navigation.AuthNavigation

class AuthNavigationImpl(
    private val navController : NavController, private val onExitApp : () -> Unit
) : AuthNavigation {

    override fun navigateToMain() {
        navController.navigate("main") {
            popUpTo("auth") { inclusive = true }
        }
    }

    override fun exitApp() {
        onExitApp()
    }
}