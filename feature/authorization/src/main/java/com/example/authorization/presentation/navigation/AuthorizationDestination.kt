package com.example.authorization.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.authorization.presentation.screen.AuthorizationScreen
import com.example.authorization.presentation.viewmodel.AuthViewModel

@Composable
fun AuthorizationDestination(
    viewModelFactory : ViewModelProvider.Factory
) {
    val viewModel : AuthViewModel = viewModel(factory = viewModelFactory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AuthorizationScreen(
        viewModel = viewModel, uiState = uiState
    )
}