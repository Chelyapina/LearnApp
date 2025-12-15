package com.example.navigation

import androidx.navigation.NavController
import com.example.deck.presentation.navigation.DeckNavigation

class DeckNavigationImpl(
    private val navController : NavController, private val onExitApp : () -> Unit
) : DeckNavigation {

    override fun navigateToAuth() {
        navController.navigate("auth") {
            popUpTo("main") { inclusive = true }
        }
    }

    override fun exitApp() {
        onExitApp()
    }
}