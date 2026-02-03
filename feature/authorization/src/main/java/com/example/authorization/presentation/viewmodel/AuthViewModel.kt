package com.example.authorization.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authorization.domain.entity.Auth
import com.example.authorization.domain.usecase.ScenarioLoginUseCase
import com.example.authorization.presentation.state.AuthEvent
import com.example.authorization.presentation.state.AuthNavigationEvent
import com.example.authorization.presentation.state.AuthScreen
import com.example.authorization.presentation.state.AuthUiState
import com.example.authorization.presentation.validation.AuthValidation
import com.example.designsystem.components.alert.model.AlertData
import com.example.designsystem.state.LoadError
import com.example.designsystem.state.LoadingState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val scenarioLoginUseCase : ScenarioLoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState(isLoading = LoadingState.Idle))
    val uiState : StateFlow<AuthUiState> = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<AuthNavigationEvent>()
    val navigationEvent : SharedFlow<AuthNavigationEvent> = _navigationEvent.asSharedFlow()

    fun handleEvent(event : AuthEvent) {
        when (event) {
            is AuthEvent.EmailChanged -> updateEmail(event.email)
            is AuthEvent.PasswordChanged -> updatePassword(event.password)
            is AuthEvent.SubmitLogin -> submitLogin()
            is AuthEvent.SubmitPassword -> submitPassword()
            is AuthEvent.BackPressed -> handleBackPressed()
            is AuthEvent.AlertHandled -> handleAlertDismissed()
        }
    }

    private fun updateEmail(email : String) {
        _uiState.update { state ->
            state.copy(
                email = email, emailError = null
            )
        }
    }

    private fun updatePassword(password : String) {
        _uiState.update { state ->
            state.copy(
                password = password, passwordError = null
            )
        }
    }

    private fun submitLogin() {
        val email = _uiState.value.email.trim()

        when (val validationResult = AuthValidation.validateEmail(email)) {
            is AuthValidation.ValidationResult.Invalid -> {
                _uiState.update { state ->
                    state.copy(
                        emailError = validationResult.errorMessage, isLoading = LoadingState.Error(
                            LoadError.Validation(validationResult.errorMessage)
                        )
                    )
                }
                return
            }

            is AuthValidation.ValidationResult.Valid -> {
                _uiState.update { state ->
                    state.copy(
                        screen = AuthScreen.Password(email = email),
                        emailError = null,
                        isLoading = LoadingState.Idle
                    )
                }
            }
        }
    }

    private fun submitPassword() {
        val currentState = _uiState.value
        val email = currentState.email.trim()
        val password = currentState.password.trim()

        when (val validationResult = AuthValidation.validatePassword(password)) {
            is AuthValidation.ValidationResult.Invalid -> {
                _uiState.update { state ->
                    state.copy(
                        passwordError = validationResult.errorMessage,
                        isLoading = LoadingState.Error(
                            LoadError.Validation(validationResult.errorMessage)
                        )
                    )
                }
                return
            }

            is AuthValidation.ValidationResult.Valid -> {
                _uiState.update { it.copy(isLoading = LoadingState.Loading) }

                viewModelScope.launch {
                    val credentials = Auth(
                        login = email, password = password
                    )

                    val result = scenarioLoginUseCase(credentials)

                    when (result) {
                        is ScenarioLoginUseCase.Result.Success -> {
                            _uiState.update { state ->
                                state.copy(isLoading = LoadingState.Success)
                            }
                            _navigationEvent.emit(AuthNavigationEvent.NavigateToMain)
                        }

                        is ScenarioLoginUseCase.Result.Error -> {
                            handleLoginError(result.error)
                        }
                    }
                }
            }
        }
    }

    private fun handleLoginError(error : LoadError) {
        _uiState.update { state ->
            state.copy(
                isLoading = LoadingState.Error(error)
            )
        }

        when (error) {
            is LoadError.Validation -> {
                // Уже обработано в валидации
            }

            LoadError.Unauthorized -> {
                _uiState.update { state ->
                    state.copy(
                        passwordError = INVALID_CREDENTIALS_ERROR
                    )
                }
            }

            else -> {
                if (error.requiresAlert()) {
                    _uiState.update { state ->
                        state.copy(
                            alertData = createAlertData()
                        )
                    }
                }
            }
        }
    }

    private fun handleBackPressed() {
        val currentState = _uiState.value

        when (currentState.screen) {
            is AuthScreen.Password -> {
                _uiState.update { state ->
                    state.copy(
                        screen = AuthScreen.Login,
                        password = "",
                        passwordError = null,
                        isPasswordVisible = false,
                        isLoading = LoadingState.Idle,
                    )
                }
            }

            is AuthScreen.Login -> {
                viewModelScope.launch {
                    _navigationEvent.emit(AuthNavigationEvent.ExitApp)
                }
            }
        }
    }

    private fun handleAlertDismissed() {
        _uiState.update { state ->
            state.copy(
                screen = AuthScreen.Login,
                alertData = null,
                isLoading = LoadingState.Idle,
                password = "",
                passwordError = null,
                isPasswordVisible = false,
                email = "",
                emailError = null
            )
        }
    }

    fun togglePasswordVisibility() {
        _uiState.update { state ->
            state.copy(
                isPasswordVisible = !state.isPasswordVisible
            )
        }
    }

    private fun createAlertData() : AlertData {

        return AlertData(
            title = "",
            message = "",
            confirmText = "",
            onConfirm = { handleEvent(AuthEvent.AlertHandled) })
    }

    companion object {
        private const val INVALID_CREDENTIALS_ERROR = "Неверный email или пароль"
    }
}