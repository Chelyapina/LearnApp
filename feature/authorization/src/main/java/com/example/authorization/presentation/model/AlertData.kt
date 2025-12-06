package com.example.authorization.presentation.model

data class AlertData(
    val title : String, val message : String, val onConfirm : () -> Unit
)