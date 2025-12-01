package com.example.learnapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.authorization.presentation.screen.AuthorizationScreen
import com.example.authorization.presentation.viewmodel.AuthViewModel
import com.example.deck.presentation.screen.DeckScreen
import com.example.deck.presentation.viewmodel.DeckViewModel
import com.example.designsystem.theme.LearnAppTheme
import com.example.navigation.AppNavigator
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory

    private var currentScreen : Screen by mutableStateOf(Screen.Auth)

    override fun onCreate(savedInstanceState : Bundle?) {
        (application as LearnApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        val navigator = object : AppNavigator {
            override fun navigateToMain() {
                currentScreen = Screen.Deck
            }

            override fun navigateToAuth() {
                currentScreen = Screen.Auth
            }

            override fun popBackStack() {
                onBackPressedDispatcher.onBackPressed()
            }

            override fun exitApp() {
                finishAffinity()
            }
        }

        setContent {
            LearnAppTheme {
                when (currentScreen) {
                    Screen.Auth -> {
                        val authViewModel : AuthViewModel = viewModel(
                            factory = viewModelFactory, key = "AuthViewModel"
                        )
                        AuthorizationScreen(
                            viewModel = authViewModel, navigator = navigator
                        )
                    }

                    Screen.Deck -> {
                        val deckViewModel : DeckViewModel = viewModel(
                            factory = viewModelFactory, key = "DeckViewModel"
                        )
                        DeckScreen(
                            viewModel = deckViewModel, navigator = navigator
                        )
                    }
                }
            }
        }
    }

    private sealed class Screen {
        data object Auth : Screen()
        data object Deck : Screen()
    }
}