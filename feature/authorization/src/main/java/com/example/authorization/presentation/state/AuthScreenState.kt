package com.example.authorization.presentation.state

sealed class AuthScreenState {
    data object Login : AuthScreenState()
    data class Password(val email : String) : AuthScreenState()
}