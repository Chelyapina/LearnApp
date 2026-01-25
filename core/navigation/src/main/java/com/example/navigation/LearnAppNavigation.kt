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
            is SplashViewModel.AuthState.Authenticated -> {
                navController.navigate("main") {
                    popUpTo("splash") { inclusive = true }
                }
            }

            is SplashViewModel.AuthState.Loading -> {
                // Перехода нет тк загрузка осуществляется на SplashScreen
            }

            is SplashViewModel.AuthState.Error, is SplashViewModel.AuthState.Unauthenticated -> {
                navController.navigate("auth") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }
    }

    BackHandler(enabled = true) {
        val currentDestination = navController.currentDestination?.route
        val backStack = navController.currentBackStack.value

        val hasPreviousScreen = backStack.any {
            it.destination.route != currentDestination
        }

        when (currentDestination) {
            "auth" -> {
                if (hasPreviousScreen) {
                    navController.popBackStack()
                } else {
                    onExitApp()
                }
            }

            "main" -> {
                navController.navigate("auth") {
                    popUpTo("main") { inclusive = true }
                }
            }

            else -> {
                if (hasPreviousScreen) {
                    navController.popBackStack()
                } else {
                    onExitApp()
                }
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