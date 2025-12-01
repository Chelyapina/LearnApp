package com.example.authorization.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authorization.domain.entity.Auth
import com.example.authorization.domain.exception.AuthException
import com.example.authorization.domain.usecase.LoginUseCase
import com.example.authorization.domain.usecase.SaveTokenUseCase
import com.example.authorization.presentation.model.AlertData
import com.example.authorization.presentation.state.AuthScreenState
import com.example.navigation.AppNavigator
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val loginUseCase : LoginUseCase, private val saveTokenUseCase : SaveTokenUseCase
) : ViewModel() {
    private val _screenState = MutableStateFlow<AuthScreenState>(AuthScreenState.Login)
    val screenState : StateFlow<AuthScreenState> = _screenState

    private val _isLoading = MutableStateFlow(false)
    val isLoading : StateFlow<Boolean> = _isLoading

    private val _showAlert = MutableSharedFlow<AlertData>()
    val showAlert : SharedFlow<AlertData> = _showAlert

    private var currentEmail = ""

    fun onLoginSubmit(email : String) {
        currentEmail = email
        _screenState.value = AuthScreenState.Password(email)
    }

    fun onBackClick(navigator : AppNavigator) {
        when (_screenState.value) {
            is AuthScreenState.Password -> {
                _screenState.value = AuthScreenState.Login
            }

            is AuthScreenState.Login -> {
                navigator.exitApp()
            }
        }
    }

    fun onPasswordSubmit(email : String, password : String, navigator : AppNavigator) {
        viewModelScope.launch {
            _isLoading.value = true
            Log.d("Auth", "Password submitted for: $email")

            try {
                val auth = Auth(email, password)
                val token = loginUseCase(auth)
                saveTokenUseCase(token)

                Log.i("Auth", "Login completed successfully")
                navigator.navigateToMain()

            } catch (e : AuthException) {
                Log.w("Auth", "Authentication failed")
                _showAlert.emit(
                    AlertData(
                        title = "Login Failed",
                        message = "Invalid email or password\n\nTest credentials:\nuser@test.com / 0000",
                        onConfirm = {
                            _screenState.value = AuthScreenState.Login
                        })
                )

            } catch (e : Exception) {
                Log.e("Auth", "Login error", e)
                _showAlert.emit(
                    AlertData(
                        title = "Error",
                        message = "Network error. Please try again.",
                        onConfirm = { })
                )

            } finally {
                _isLoading.value = false
            }
        }
    }
}