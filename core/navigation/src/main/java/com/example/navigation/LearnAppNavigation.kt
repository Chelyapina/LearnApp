package com.example.navigation

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
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
import com.example.deck.presentation.state.DeckNavigationEvent
import com.example.deck.presentation.viewmodel.DeckViewModel
import com.example.deck.profile.ProfileScreen
import com.example.models.AuthState
import com.example.splash.presentation.SplashViewModel

@SuppressLint("RestrictedApi")
@Composable
fun LearnAppNavigation(
    viewModelFactory : ViewModelProvider.Factory, onExitApp : () -> Unit
) {
    val navController = rememberNavController()

    val splashViewModel : SplashViewModel = viewModel(
        factory = viewModelFactory, key = "splash"
    )

    val deckViewModel: DeckViewModel = viewModel(
        factory = viewModelFactory, key = "deck"
    )

    val authState by splashViewModel.authState.collectAsStateWithLifecycle()

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> {
                navController.navigate("main") {
                    popUpTo("splash") { inclusive = true }
                }
            }

            is AuthState.Loading -> {
                // Перехода нет тк загрузка осуществляется на SplashScreen
            }

            is AuthState.Error, is AuthState.Unauthenticated -> {
                navController.navigate("auth") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        deckViewModel.navigationEvent.collect { event ->
            when (event) {
                is DeckNavigationEvent.NavigateToAuth -> {
                    navController.navigate("auth") {
                        popUpTo(0) { inclusive = true }
                    }
                }
                is DeckNavigationEvent.NavigateToProfile -> {
                    navController.navigate("profile")
                }
                is DeckNavigationEvent.NavigateBack -> {
                    navController.popBackStack()
                }
            }
        }
    }

    BackHandler(enabled = true) {
        val currentDestination = navController.currentDestination?.route
        val backStack = navController.currentBackStack.value
        val canGoBack = backStack.size > 1

        when (currentDestination) {
            "profile" -> {
                navController.popBackStack()
            }

            "main" -> {
                val isProfileInBackStack = backStack.any { it.destination.route == "profile" }
                if (isProfileInBackStack) {
                    navController.popBackStack()
                } else {
                    onExitApp()
                }
            }

            "splash" -> {
                onExitApp()
            }

            else -> {
                if (canGoBack) {
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
        }

        composable("auth") { backStackEntry ->
            BackHandler(enabled = true) {
                val backStack = navController.currentBackStack.value
                val canGoBack = backStack.size > 1

                if (canGoBack) {
                    navController.popBackStack()
                } else {
                    onExitApp()
                }
            }

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
                viewModel = deckViewModel,
                navigation = deckNavigation
            )
        }

        composable("profile") {
            ProfileScreen(
                viewModel = deckViewModel,
                onBackClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}