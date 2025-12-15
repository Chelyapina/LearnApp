package com.example.authorization.presentation.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.authorization.R
import com.example.authorization.presentation.state.AuthEvent
import com.example.authorization.presentation.state.AuthScreen
import com.example.authorization.presentation.state.AuthUiState
import com.example.authorization.presentation.viewmodel.AuthViewModel
import com.example.designsystem.components.appbar.AppBarState
import com.example.designsystem.components.appbar.CommonAppBar
import com.example.designsystem.components.loading.LoadingScreen
import com.example.designsystem.state.AlertConfig
import com.example.designsystem.state.LoadingState
import com.example.designsystem.state.getConfirmText
import com.example.designsystem.state.getMessage
import com.example.designsystem.state.getTitle

@Composable
fun AuthorizationScreen(
    viewModel : AuthViewModel, uiState : AuthUiState
) {
    BackHandler(enabled = true) {
        viewModel.handleEvent(AuthEvent.BackPressed)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AuthorizationScreenContent(
            viewModel = viewModel, uiState = uiState
        )

        when (uiState.isLoading) {
            is LoadingState.Loading -> {
                LoadingScreen(
                    modifier = Modifier.fillMaxSize(), text = R.string.load_word
                )
            }

            else -> {}
        }
    }

    uiState.alertData?.let { alertData ->
        val alertConfig = when (val loadingState = uiState.isLoading) {
            is LoadingState.Error -> loadingState.error.toAlertConfig()
            else -> AlertConfig.GenericError
        }

        AlertDialog(
            onDismissRequest = { viewModel.handleEvent(AuthEvent.AlertHandled) }, title = {
            Text(text = alertConfig.getTitle())
        }, text = {
            Text(text = alertConfig.getMessage())
        }, confirmButton = {
            Button(
                onClick = alertData.onConfirm, colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(text = alertConfig.getConfirmText())
            }
        }, dismissButton = null, shape = RoundedCornerShape(12.dp)
        )
    }
}

@Composable
fun AuthorizationScreenContent(
    modifier : Modifier = Modifier, viewModel : AuthViewModel, uiState : AuthUiState
) {
    Scaffold(
        topBar = {
            CommonAppBar(
                state = when (uiState.screen) {
                    is AuthScreen.Login -> AppBarState.Empty
                    is AuthScreen.Password -> AppBarState.Back
                }, onBackClick = { viewModel.handleEvent(AuthEvent.BackPressed) }
            )
        }, modifier = modifier.systemBarsPadding()
    ) { paddingValues ->
        AuthorizationContent(
            uiState = uiState,
            onEvent = viewModel::handleEvent,
            onTogglePasswordVisibility = { viewModel.togglePasswordVisibility() },
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}