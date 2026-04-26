package com.example.settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.designsystem.components.alert.model.AlertData
import com.example.designsystem.components.appbar.AppBarState
import com.example.designsystem.components.appbar.CommonAppBar
import com.example.designsystem.state.LoadingState
import com.example.settings.R

@Composable
fun SettingsScreen(
    uiState : SettingsUiState,
    isLoadingState : LoadingState,
    alertData : AlertData?,
    onLimitNewWordsChange : (Int) -> Unit,
    onLimitWordsForRepeatChange : (Int) -> Unit,
    onNewPasswordChange : (String) -> Unit,
    onConfirmPasswordChange : (String) -> Unit,
    onOldPasswordChange : (String) -> Unit,
    onSaveClick : () -> Unit,
    onAlertDismissed : () -> Unit,
    onBackClick : () -> Unit
) {
    val currentState = when (uiState) {
        is SettingsUiState.Success -> uiState
        SettingsUiState.Loading -> SettingsUiState.Success()
    }

    var isNewPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }
    var isOldPasswordVisible by remember { mutableStateOf(false) }

    val isLoading = isLoadingState == LoadingState.Loading

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                CommonAppBar(
                    modifier = Modifier.padding(16.dp),
                    state = AppBarState.Back(title = stringResource(id = R.string.settings_title)),
                    onBackClick = onBackClick
                )
            }, modifier = Modifier.systemBarsPadding()
        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                LimitsSection(
                    limitNewWords = currentState.limitNewWords,
                    limitWordsForRepeat = currentState.limitWordsForRepeat,
                    onLimitNewWordsChange = onLimitNewWordsChange,
                    onLimitWordsForRepeatChange = onLimitWordsForRepeatChange
                )

                Spacer(Modifier.size(32.dp))

                PasswordSection(
                    newPassword = currentState.newPassword,
                    confirmPassword = currentState.confirmPassword,
                    passwordError = if (currentState.newPassword.isNotBlank() && currentState.confirmPassword.isNotBlank() && currentState.newPassword != currentState.confirmPassword) stringResource(
                        R.string.password_mismatch
                    ) else null,
                    onNewPasswordChange = onNewPasswordChange,
                    onConfirmPasswordChange = onConfirmPasswordChange,
                    isNewPasswordVisible = isNewPasswordVisible,
                    isConfirmPasswordVisible = isConfirmPasswordVisible,
                    onNewPasswordVisibilityToggle = {
                        isNewPasswordVisible = !isNewPasswordVisible
                    },
                    onConfirmPasswordVisibilityToggle = {
                        isConfirmPasswordVisible = !isConfirmPasswordVisible
                    })

                Spacer(Modifier.size(32.dp))

                ConfirmChangesSection(
                    oldPassword = currentState.oldPassword,
                    isSaveEnabled = currentState.oldPassword.isNotBlank() && !isLoading,
                    isLoading = isLoading,
                    onOldPasswordChange = onOldPasswordChange,
                    onSaveClick = onSaveClick,
                    isOldPasswordVisible = isOldPasswordVisible,
                    onOldPasswordVisibilityToggle = {
                        isOldPasswordVisible = !isOldPasswordVisible
                    })

                Spacer(Modifier.size(32.dp))
            }
        }

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }

    alertData?.let { alert ->
        AlertDialog(
            onDismissRequest = onAlertDismissed,
            title = { Text(text = alert.title) },
            text = { Text(text = alert.message) },
            confirmButton = {
                Button(
                    onClick = {
                        alert.onConfirm()
                        onAlertDismissed()
                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(text = alert.confirmText)
                }
            },
            dismissButton = null,
            shape = RoundedCornerShape(12.dp)
        )
    }
}

@Composable
private fun LimitsSection(
    limitNewWords : Int,
    limitWordsForRepeat : Int,
    onLimitNewWordsChange : (Int) -> Unit,
    onLimitWordsForRepeatChange : (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = stringResource(R.string.limits_title),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            SliderWithLabel(
                label = stringResource(R.string.limit_new_words),
                value = limitNewWords,
                onValueChange = onLimitNewWordsChange
            )

            SliderWithLabel(
                label = stringResource(R.string.limit_words_for_repeat),
                value = limitWordsForRepeat,
                onValueChange = onLimitWordsForRepeatChange
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SliderWithLabel(
    label : String, value : Int, onValueChange : (Int) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(label, style = MaterialTheme.typography.bodyMedium)
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary, shape = CircleShape
                    )
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        shape = CircleShape
                    ), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = value.toString(),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Slider(
            value = value.toFloat(),
            onValueChange = { onValueChange(it.toInt()) },
            valueRange = 3f..30f,
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.primary,
                inactiveTrackColor = MaterialTheme.colorScheme.surface,
                activeTickColor = MaterialTheme.colorScheme.onPrimary,
                inactiveTickColor = MaterialTheme.colorScheme.onSurface
            ),
            thumb = {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary, shape = CircleShape
                        )
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary,
                            shape = CircleShape
                        ), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = value.toString(),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            })

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.min_value_3),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = stringResource(R.string.max_value_30),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun PasswordSection(
    newPassword : String,
    confirmPassword : String,
    passwordError : String?,
    onNewPasswordChange : (String) -> Unit,
    onConfirmPasswordChange : (String) -> Unit,
    isNewPasswordVisible : Boolean,
    isConfirmPasswordVisible : Boolean,
    onNewPasswordVisibilityToggle : () -> Unit,
    onConfirmPasswordVisibilityToggle : () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.change_password_title),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            SettingsPasswordField(
                value = newPassword,
                onValueChange = onNewPasswordChange,
                label = stringResource(R.string.new_password_optional),
                isPassword = true,
                isPasswordVisible = isNewPasswordVisible,
                onPasswordVisibilityToggle = onNewPasswordVisibilityToggle
            )

            SettingsPasswordField(
                value = confirmPassword,
                onValueChange = onConfirmPasswordChange,
                label = stringResource(R.string.confirm_new_password),
                isPassword = true,
                isPasswordVisible = isConfirmPasswordVisible,
                onPasswordVisibilityToggle = onConfirmPasswordVisibilityToggle,
                isError = passwordError != null,
                error = passwordError,
                enabled = newPassword.isNotBlank()
            )
        }
    }
}

@Composable
private fun ConfirmChangesSection(
    oldPassword : String, isSaveEnabled : Boolean, isLoading : Boolean,
    onOldPasswordChange : (String) -> Unit,
    onSaveClick : () -> Unit,
    isOldPasswordVisible : Boolean,
    onOldPasswordVisibilityToggle : () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.confirm_changes_title),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            SettingsPasswordField(
                value = oldPassword,
                onValueChange = onOldPasswordChange,
                label = stringResource(R.string.old_password_required),
                isPassword = true,
                isPasswordVisible = isOldPasswordVisible,
                onPasswordVisibilityToggle = onOldPasswordVisibilityToggle,
                isError = oldPassword.isBlank(),
                supportingText = {
                    if (oldPassword.isBlank()) {
                        Text(
                            text = stringResource(R.string.required_field),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                })

            Button(
                onClick = onSaveClick, enabled = isSaveEnabled, modifier = Modifier.fillMaxWidth()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                } else {
                    Text(stringResource(R.string.save_button))
                }
            }
        }
    }
}

@Composable
private fun SettingsPasswordField(
    modifier : Modifier = Modifier,
    value : String,
    onValueChange : (String) -> Unit,
    label : String,
    isPassword : Boolean = true,
    isPasswordVisible : Boolean = false,
    onPasswordVisibilityToggle : () -> Unit = {},
    error : String? = null,
    isError : Boolean = false,
    enabled : Boolean = true,
    supportingText : (@Composable () -> Unit)? = null
) {
    val trailingIcon : (@Composable () -> Unit)? = if (isPassword) {
        {
            IconButton(onClick = onPasswordVisibilityToggle) {
                Icon(
                    imageVector = if (isPasswordVisible) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    },
                    contentDescription = if (isPasswordVisible) stringResource(R.string.visible_password)
                    else stringResource(R.string.no_visible_password)
                )
            }
        }
    } else {
        null
    }

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = { if (it.length <= 30) onValueChange(it) },
        label = { Text(text = label) },
        shape = RoundedCornerShape(6.dp),
        visualTransformation = if (isPassword && !isPasswordVisible) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        trailingIcon = trailingIcon,
        singleLine = true,
        isError = isError || error != null,
        enabled = enabled,
        supportingText = supportingText ?: {
            if (error != null) {
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        })
}