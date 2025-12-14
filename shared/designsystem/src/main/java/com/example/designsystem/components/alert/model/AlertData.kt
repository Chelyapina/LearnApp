package com.example.designsystem.components.alert.model

data class AlertData(
    val title : String,
    val message : String,
    val confirmText : String,
    val onConfirm : () -> Unit = {}
)