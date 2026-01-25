package com.example.authorization.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.authorization.R
import com.example.authorization.presentation.state.AuthEvent
import com.example.authorization.presentation.state.AuthScreen
import com.example.authorization.presentation.state.AuthUiState
import com.example.designsystem.theme.customOnBackgroundColor

@Composable
fun AuthorizationContent(
    modifier : Modifier = Modifier,
    uiState : AuthUiState,
    onEvent : (AuthEvent) -> Unit,
    onTogglePasswordVisibility : () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(45.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = when (uiState.screen) {
                is AuthScreen.Login -> stringResource(R.string.login_title)
                is AuthScreen.Password -> stringResource(R.string.password_title)
            },
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        when (uiState.screen) {
            is AuthScreen.Login -> {
                Text(
                    text = stringResource(R.string.login_subtitle),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.customOnBackgroundColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            is AuthScreen.Password -> {}
        }

        when (uiState.screen) {
            is AuthScreen.Login -> {
                LoginTextField(
                    email = uiState.email,
                    emailError = uiState.emailError,
                    onEmailChanged = { onEvent(AuthEvent.EmailChanged(it)) },
                    onSubmit = { onEvent(AuthEvent.SubmitLogin) },
                    label = stringResource(R.string.login_textfield)
                )
            }

            is AuthScreen.Password -> {
                PasswordTextField(
                    password = uiState.password,
                    isPasswordVisible = uiState.isPasswordVisible,
                    passwordError = uiState.passwordError,
                    onPasswordChanged = { onEvent(AuthEvent.PasswordChanged(it)) },
                    onSubmit = { onEvent(AuthEvent.SubmitPassword) },
                    onTogglePasswordVisibility = onTogglePasswordVisibility,
                    label = stringResource(R.string.password_textfield)
                )
            }
        }
    }
}

@Composable
private fun LoginTextField(
    email : String,
    emailError : String?,
    onEmailChanged : (String) -> Unit,
    onSubmit : () -> Unit,
    label : String,
    modifier : Modifier = Modifier
) {
    AuthorizationTextField(
        value = email,
        onValueChange = onEmailChanged,
        label = label,
        isPassword = false,
        isPasswordVisible = false,
        onPasswordVisibilityToggle = { },
        onSubmit = onSubmit,
        error = emailError,
        modifier = modifier
    )
}

@Composable
private fun PasswordTextField(
    password : String,
    isPasswordVisible : Boolean,
    passwordError : String?,
    onPasswordChanged : (String) -> Unit,
    onSubmit : () -> Unit,
    onTogglePasswordVisibility : () -> Unit,
    label : String,
    modifier : Modifier = Modifier
) {
    AuthorizationTextField(
        value = password,
        onValueChange = onPasswordChanged,
        label = label,
        isPassword = true,
        isPasswordVisible = isPasswordVisible,
        onPasswordVisibilityToggle = onTogglePasswordVisibility,
        onSubmit = onSubmit,
        error = passwordError,
        modifier = modifier
    )
}

@Composable
private fun AuthorizationTextField(
    modifier : Modifier = Modifier,
    value : String,
    onValueChange : (String) -> Unit,
    label : String,
    isPassword : Boolean,
    isPasswordVisible : Boolean = false,
    onPasswordVisibilityToggle : () -> Unit = {},
    onSubmit : () -> Unit,
    error : String? = null
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

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                label = { Text(text = label) },
                shape = RoundedCornerShape(6.dp),
                visualTransformation = if (isPassword && !isPasswordVisible) {
                    PasswordVisualTransformation()
                } else {
                    VisualTransformation.None
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onSubmit() }),
                trailingIcon = trailingIcon,
                singleLine = true,
                isError = error != null,
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Row(
            modifier = Modifier.padding(top = 6.dp),
        ) {
            FilledIconButton(
                onClick = onSubmit,
                enabled = value.isNotBlank(),
                shape = RoundedCornerShape(12.dp),
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.38f),
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.38f)
                ),
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    modifier = Modifier.size(38.dp),
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = stringResource(R.string.arrow_right)
                )
            }
        }
    }

    if (error != null) {
        Text(
            modifier = Modifier
                .padding(start = 12.dp, top = 4.dp)
                .fillMaxWidth(),
            text = error,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall
        )
    }
}