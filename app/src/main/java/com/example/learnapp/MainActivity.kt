package com.example.learnapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.deck.presentation.screen.DeckScreen
import com.example.deck.presentation.viewmodel.DeckViewModel
import com.example.deck.presentation.di.DeckViewModelFactory

import com.example.designsystem.theme.LearnAppTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModelFactory: DeckViewModelFactory

    private val viewModel: DeckViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState : Bundle?) {
        (application as LearnApp).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearnAppTheme {
                DeckScreen(viewModel = viewModel)
            }
        }
    }
}