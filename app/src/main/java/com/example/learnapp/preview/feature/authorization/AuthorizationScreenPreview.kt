package com.example.learnapp.preview.feature.authorization

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.authorization.presentation.screen.AuthorizationContent
import com.example.authorization.presentation.state.AuthScreen
import com.example.authorization.presentation.state.AuthUiState
import com.example.designsystem.state.LoadingState
import com.example.designsystem.theme.LearnAppTheme

@Preview(showBackground = true)
@Composable
fun AuthorizationContentPreview_LoginStateLight() {
    LearnAppTheme {
        AuthorizationContent(
            uiState = AuthUiState(
            screen = AuthScreen.Login, email = "user@example.com", isLoading = LoadingState.Idle
        ), onEvent = {}, onTogglePasswordVisibility = {})
    }
}

@Preview(showBackground = true)
@Composable
fun AuthorizationContentPreview_LoginStateWithError() {
    LearnAppTheme {
        AuthorizationContent(
            uiState = AuthUiState(
            screen = AuthScreen.Login,
            email = "invalid.email",
            isLoading = LoadingState.Idle,
            emailError = "Некорректный email"
        ), onEvent = {}, onTogglePasswordVisibility = {})
    }
}

@Preview(showBackground = true)
@Composable
fun AuthorizationContentPreview_PasswordStateLight() {
    LearnAppTheme {
        AuthorizationContent(
            uiState = AuthUiState(
            screen = AuthScreen.Password(email = "user@example.com"),
            email = "user@example.com",
            password = "mypassword123",
            isPasswordVisible = false,
            isLoading = LoadingState.Idle
        ), onEvent = {}, onTogglePasswordVisibility = {})
    }
}

@Preview(showBackground = true)
@Composable
fun AuthorizationContentPreview_PasswordStateWithError() {
    LearnAppTheme {
        AuthorizationContent(
            uiState = AuthUiState(
            screen = AuthScreen.Password(email = "user@example.com"),
            email = "user@example.com",
            password = "123",
            isPasswordVisible = false,
            isLoading = LoadingState.Idle,
            passwordError = "Пароль слишком короткий"
        ), onEvent = {}, onTogglePasswordVisibility = {})
    }
}

@Preview(showBackground = true)
@Composable
fun AuthorizationContentPreview_PasswordStateVisible() {
    LearnAppTheme {
        AuthorizationContent(
            uiState = AuthUiState(
            screen = AuthScreen.Password(email = "user@example.com"),
            email = "user@example.com",
            password = "mypassword123",
            isPasswordVisible = true,
            isLoading = LoadingState.Idle
        ), onEvent = {}, onTogglePasswordVisibility = {})
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AuthorizationContentPreview_LoginStateDark() {
    LearnAppTheme {
        AuthorizationContent(
            uiState = AuthUiState(
            screen = AuthScreen.Login, email = "user@example.com", isLoading = LoadingState.Idle
        ), onEvent = {}, onTogglePasswordVisibility = {})
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AuthorizationContentPreview_PasswordStateDark() {
    LearnAppTheme {
        AuthorizationContent(
            uiState = AuthUiState(
            screen = AuthScreen.Password(email = "user@example.com"),
            email = "user@example.com",
            password = "mypassword123",
            isPasswordVisible = false,
            isLoading = LoadingState.Idle
        ), onEvent = {}, onTogglePasswordVisibility = {})
    }
}