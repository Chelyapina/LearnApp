package com.example.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.designsystem.components.alert.model.AlertData
import com.example.designsystem.state.LoadingState
import com.example.network.exception.NetworkException
import com.example.settings.domain.usecase.GetSettingsUseCase
import com.example.settings.domain.usecase.UpdateSettingsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val getSettingsUseCase : GetSettingsUseCase,
    private val updateSettingsUseCase : UpdateSettingsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<SettingsUiState>(SettingsUiState.Loading)
    val uiState : StateFlow<SettingsUiState> = _uiState.asStateFlow()

    private val _loadingState = MutableStateFlow<LoadingState>(LoadingState.Idle)
    val loadingState : StateFlow<LoadingState> = _loadingState.asStateFlow()

    private val _alertData = MutableStateFlow<AlertData?>(null)
    val alertData : StateFlow<AlertData?> = _alertData.asStateFlow()

    private val _shouldClose = MutableStateFlow(false)
    val shouldClose : StateFlow<Boolean> = _shouldClose.asStateFlow()

    private var isSaving = false

    init {
        loadSettings()
    }

    fun handleEvent(event : SettingsEvent) {
        when (event) {
            is SettingsEvent.LimitNewWordsChanged -> updateLimitNewWords(event.value)
            is SettingsEvent.LimitWordsForRepeatChanged -> updateLimitWordsForRepeat(event.value)
            is SettingsEvent.NewPasswordChanged -> updateNewPassword(event.value)
            is SettingsEvent.ConfirmPasswordChanged -> updateConfirmPassword(event.value)
            is SettingsEvent.OldPasswordChanged -> updateOldPassword(event.value)
            SettingsEvent.SaveSettings -> {
                if (isSaving || _loadingState.value == LoadingState.Loading) {
                    return
                }
                saveSettings()
            }

            SettingsEvent.AlertHandled -> dismissAlert()
        }
    }

    private fun loadSettings() {
        viewModelScope.launch {
            _loadingState.value = LoadingState.Loading

            val result = getSettingsUseCase()

            result.onSuccess { settings ->
                _uiState.value = SettingsUiState.Success(
                    limitNewWords = settings.limitNewWords,
                    limitWordsForRepeat = settings.limitWordsForRepeat,
                    newPassword = "",
                    confirmPassword = "",
                    oldPassword = ""
                )
                _loadingState.value = LoadingState.Idle
            }.onFailure { error ->
                _loadingState.value = LoadingState.Idle
                showAlert(error.message ?: LOAD_SETTINGS_ERROR_MESSAGE)
            }
        }
    }

    private fun saveSettings() {
        if (isSaving) return

        val currentState = _uiState.value
        if (currentState !is SettingsUiState.Success) return

        if (currentState.oldPassword.isBlank()) {
            showAlert(ENTER_OLD_PASSWORD_MESSAGE)
            return
        }

        val hasPasswordChange = currentState.newPassword.isNotBlank()
        if (hasPasswordChange && currentState.newPassword != currentState.confirmPassword) {
            showAlert(PASSWORD_MISMATCH_MESSAGE)
            return
        }

        viewModelScope.launch {
            isSaving = true
            _loadingState.value = LoadingState.Loading

            val result = updateSettingsUseCase(
                oldPassword = currentState.oldPassword,
                limitNewWords = currentState.limitNewWords,
                limitWordsForRepeat = currentState.limitWordsForRepeat,
                newPassword = if (hasPasswordChange) currentState.newPassword else null
            )

            result.onSuccess { settings ->
                _uiState.update { state ->
                    if (state is SettingsUiState.Success) {
                        state.copy(
                            limitNewWords = settings.limitNewWords,
                            limitWordsForRepeat = settings.limitWordsForRepeat,
                            newPassword = "",
                            confirmPassword = "",
                            oldPassword = ""
                        )
                    } else state
                }
                _loadingState.value = LoadingState.Idle
                showSuccessAndClose(SAVE_SUCCESS_MESSAGE)
            }.onFailure { error ->
                _loadingState.value = LoadingState.Idle

                _uiState.update { state ->
                    if (state is SettingsUiState.Success) {
                        state.copy(oldPassword = "")
                    } else state
                }

                val message = when (error) {
                    is NetworkException.ServerError -> error.errorMessage ?: SERVER_ERROR_MESSAGE
                    is NetworkException.UnauthorizedError -> error.errorMessage
                                                             ?: UNAUTHORIZED_MESSAGE

                    is NetworkException.NetworkError -> NETWORK_ERROR_MESSAGE
                    is NetworkException.TimeoutError -> TIMEOUT_ERROR_MESSAGE
                    else -> error.message ?: UNKNOWN_ERROR_MESSAGE
                }
                showAlert(message)
            }
            isSaving = false
        }
    }

    private fun updateLimitNewWords(value : Int) {
        _uiState.update { state ->
            if (state is SettingsUiState.Success) {
                state.copy(limitNewWords = value)
            } else {
                state
            }
        }
    }

    private fun updateLimitWordsForRepeat(value : Int) {
        _uiState.update { state ->
            if (state is SettingsUiState.Success) {
                state.copy(limitWordsForRepeat = value)
            } else {
                state
            }
        }
    }

    private fun updateNewPassword(value : String) {
        _uiState.update { state ->
            if (state is SettingsUiState.Success) {
                state.copy(
                    newPassword = value,
                    confirmPassword = ""
                )
            } else {
                state
            }
        }
    }

    private fun updateConfirmPassword(value : String) {
        _uiState.update { state ->
            if (state is SettingsUiState.Success) {
                state.copy(confirmPassword = value)
            } else {
                state
            }
        }
    }

    private fun updateOldPassword(value : String) {
        _uiState.update { state ->
            if (state is SettingsUiState.Success) {
                state.copy(oldPassword = value)
            } else {
                state
            }
        }
    }

    private fun showAlert(message : String) {
        _alertData.value = AlertData(
            title = ALERT_TITLE_ERROR,
            message = message,
            confirmText = ALERT_CONFIRM_TEXT,
            onConfirm = { handleEvent(SettingsEvent.AlertHandled) })
    }

    private fun dismissAlert() {
        _alertData.value = null
    }

    private fun showSuccessAndClose(message : String) {
        _alertData.value = AlertData(
            title = ALERT_TITLE_SUCCESS,
            message = message,
            confirmText = ALERT_CONFIRM_TEXT,
            onConfirm = {
                handleEvent(SettingsEvent.AlertHandled)
                _shouldClose.value = true
            })
    }

    companion object {
        private const val LOAD_SETTINGS_ERROR_MESSAGE = "Не удалось загрузить настройки"
        private const val ENTER_OLD_PASSWORD_MESSAGE = "Введите старый пароль"
        private const val PASSWORD_MISMATCH_MESSAGE = "Пароли не совпадают"
        private const val SAVE_SUCCESS_MESSAGE = "Настройки сохранены"
        private const val SERVER_ERROR_MESSAGE = "Ошибка сервера"
        private const val UNAUTHORIZED_MESSAGE = "Не авторизован"
        private const val NETWORK_ERROR_MESSAGE = "Нет соединения с интернетом"
        private const val TIMEOUT_ERROR_MESSAGE = "Превышено время ожидания"
        private const val UNKNOWN_ERROR_MESSAGE = "Неизвестная ошибка"
        private const val ALERT_TITLE_ERROR = "Ошибка"
        private const val ALERT_TITLE_SUCCESS = "Успех"
        private const val ALERT_CONFIRM_TEXT = "OK"
    }
}

