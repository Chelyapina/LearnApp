package com.example.authorization.presentation.screen

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.example.authorization.presentation.model.AlertData

@Composable
fun AuthAlertDialog(
    alertData : AlertData?, onDismiss : () -> Unit
) {
    if (alertData != null) {
        AlertDialog(onDismissRequest = onDismiss, title = {
            Text(text = alertData.title, style = MaterialTheme.typography.headlineSmall)
        }, text = {
            Text(text = alertData.message)
        }, confirmButton = {
            TextButton(
                onClick = {
                    alertData.onConfirm()
                    onDismiss()
                }) {
                Text("OK")
            }
        })
    }
}