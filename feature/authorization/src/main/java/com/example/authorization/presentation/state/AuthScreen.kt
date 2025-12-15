package com.example.authorization.presentation.state

import com.example.designsystem.components.alert.model.AlertData
import com.example.designsystem.state.LoadingState

sealed class AuthScreen {
    data object Login : AuthScreen()
    data class Password(val email : String) : AuthScreen()
}

data class AuthUiState(
    val screen: AuthScreen = AuthScreen.Login,

    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,

    val isLoading: LoadingState,

    val emailError: String? = null,
    val passwordError: String? = null,

    val alertData: AlertData? = null
)

sealed class AuthEvent {
    data class EmailChanged(val email: String) : AuthEvent()
    data class PasswordChanged(val password: String) : AuthEvent()


    object SubmitLogin : AuthEvent()
    object SubmitPassword : AuthEvent()
    object BackPressed : AuthEvent()

    object AlertHandled : AuthEvent()
}

sealed interface AuthNavigationEvent {
    data object NavigateToMain : AuthNavigationEvent
    data object ExitApp : AuthNavigationEvent
}