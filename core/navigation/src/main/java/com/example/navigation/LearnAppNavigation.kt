package com.example.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.authorization.presentation.navigation.AuthorizationDestination
import com.example.deck.presentation.navigation.DeckDestination
import com.example.splash.presentation.SplashViewModel

@Composable
fun LearnAppNavigation(
    viewModelFactory : ViewModelProvider.Factory, onExitApp : () -> Unit
) {
    val navController = rememberNavController()

    val splashViewModel : SplashViewModel = viewModel(
        factory = viewModelFactory, key = "splash"
    )

    val authState by splashViewModel.authState.collectAsStateWithLifecycle()

    LaunchedEffect(authState) {
        when (authState) {
            SplashViewModel.AuthState.Authenticated -> {
                navController.navigate("main") {
                    popUpTo("splash") { inclusive = true }
                }
            }

            SplashViewModel.AuthState.Unauthenticated -> {
                navController.navigate("auth") {
                    popUpTo("splash") { inclusive = true }
                }
            }

            SplashViewModel.AuthState.Loading -> {
            }

            is SplashViewModel.AuthState.Error -> {
                navController.navigate("auth") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }
    }

    BackHandler(enabled = true) {
        when (navController.currentDestination?.route) {
            "auth" -> onExitApp()
            "main" -> {
                navController.navigate("auth") {
                    popUpTo("main") { inclusive = true }
                }
            }

            else -> {
                if (!navController.popBackStack()) onExitApp()
            }
        }
    }

    NavHost(
        navController = navController, startDestination = "splash"
    ) {
        composable("splash") {
            Box(Modifier.fillMaxSize())
        }

        composable("auth") { backStackEntry ->
            val authNavigation = AuthNavigationImpl(
                navController = navController, onExitApp = onExitApp
            )

            AuthorizationDestination(
                viewModelFactory = viewModelFactory, navigation = authNavigation
            )
        }

        composable("main") { backStackEntry ->
            val deckNavigation = DeckNavigationImpl(
                navController = navController, onExitApp = onExitApp
            )

            DeckDestination(
                viewModelFactory = viewModelFactory, navigation = deckNavigation
            )
        }
    }
}