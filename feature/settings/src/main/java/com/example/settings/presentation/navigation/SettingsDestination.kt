package com.example.settings.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.settings.presentation.SettingsEvent
import com.example.settings.presentation.SettingsScreen
import com.example.settings.presentation.SettingsViewModel

@Composable
fun SettingsDestination(
    viewModelFactory : ViewModelProvider.Factory, onBackClick : () -> Unit
) {
    val viewModel : SettingsViewModel = viewModel(
        factory = viewModelFactory, key = "settings"
    )

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val loadingState by viewModel.loadingState.collectAsStateWithLifecycle()
    val alertData by viewModel.alertData.collectAsStateWithLifecycle()
    val shouldClose by viewModel.shouldClose.collectAsStateWithLifecycle()

    LaunchedEffect(shouldClose) {
        if (shouldClose) {
            onBackClick()
        }
    }

    SettingsScreen(
        uiState = uiState,
        isLoadingState = loadingState,
        alertData = alertData,
        onLimitNewWordsChange = { viewModel.handleEvent(SettingsEvent.LimitNewWordsChanged(it)) },
        onLimitWordsForRepeatChange = {
            viewModel.handleEvent(
                SettingsEvent.LimitWordsForRepeatChanged(it)
            )
        },
        onNewPasswordChange = { viewModel.handleEvent(SettingsEvent.NewPasswordChanged(it)) },
        onConfirmPasswordChange = { viewModel.handleEvent(SettingsEvent.ConfirmPasswordChanged(it)) },
        onOldPasswordChange = { viewModel.handleEvent(SettingsEvent.OldPasswordChanged(it)) },
        onSaveClick = { viewModel.handleEvent(SettingsEvent.SaveSettings) },
        onAlertDismissed = { viewModel.handleEvent(SettingsEvent.AlertHandled) },
        onBackClick = onBackClick
    )
}