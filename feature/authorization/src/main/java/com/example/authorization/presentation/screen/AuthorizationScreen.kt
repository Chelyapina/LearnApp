package com.example.authorization.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.authorization.presentation.model.AlertData
import com.example.authorization.presentation.state.AuthScreenState
import com.example.authorization.presentation.viewmodel.AuthViewModel
import com.example.designsystem.components.appbar.AppBarState
import com.example.designsystem.components.appbar.CommonAppBar
import com.example.navigation.AppNavigator

@Composable
fun AuthorizationScreen(
    viewModel : AuthViewModel, navigator : AppNavigator
) {
    val screenState by viewModel.screenState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val alertData by viewModel.showAlert.collectAsStateWithLifecycle(initialValue = null)
    var currentAlert by remember { mutableStateOf<AlertData?>(null) }

    LaunchedEffect(alertData) {
        alertData?.let {
            currentAlert = it
        }
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        AuthorizationScreenContent(
            state = screenState,
            onBackClick = { viewModel.onBackClick(navigator) },
            onLoginSubmit = viewModel::onLoginSubmit,
            onPasswordSubmit = { email, password ->
                viewModel.onPasswordSubmit(email, password, navigator)
            })
    }
    AuthAlertDialog(
        alertData = currentAlert, onDismiss = { currentAlert = null })
}

@Composable
fun AuthorizationScreenContent(
    modifier : Modifier = Modifier,
    state : AuthScreenState,
    onBackClick : () -> Unit = {},
    onLoginSubmit : (email : String) -> Unit = { _ -> },
    onPasswordSubmit : (email : String, password : String) -> Unit = { _, _ -> },
) {
    Scaffold(
        topBar = {
            CommonAppBar(
                state = when (state) {
                    is AuthScreenState.Login -> AppBarState.Empty
                    is AuthScreenState.Password -> AppBarState.Back
                }, onBackClick = onBackClick
            )
        }, modifier = modifier.systemBarsPadding()
    ) { paddingValues ->
        AuthorizationContent(
            state = state,
            onLoginSubmit = onLoginSubmit,
            onPasswordSubmit = onPasswordSubmit,
            modifier = modifier.padding(paddingValues)
        )
    }
}