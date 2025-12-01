package com.example.authorization.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.authorization.R
import com.example.authorization.presentation.state.AuthScreenState
import com.example.designsystem.theme.customOnBackgroundColor

@Composable
fun AuthorizationContent(
    state : AuthScreenState,
    onLoginSubmit : (email : String) -> Unit,
    onPasswordSubmit : (email : String, password : String) -> Unit,
    modifier : Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(45.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = when (state) {
                is AuthScreenState.Login -> stringResource(R.string.login_title)
                is AuthScreenState.Password -> stringResource(R.string.password_title)
            },
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        when (state) {
            is AuthScreenState.Login -> {
                Text(
                    text = stringResource(R.string.login_subtitle),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.customOnBackgroundColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            is AuthScreenState.Password -> {}
        }

        when (state) {
            is AuthScreenState.Login -> {
                LoginTextField(
                    label = stringResource(R.string.login_textfield), onSubmit = onLoginSubmit
                )
            }

            is AuthScreenState.Password -> {
                PasswordTextField(
                    label = stringResource(R.string.password_textfield),
                    email = state.email,
                    onSubmit = onPasswordSubmit
                )
            }
        }
    }
}

@Composable
private fun LoginTextField(
    label : String, onSubmit : (email : String) -> Unit, modifier : Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }

    AuthorizationTextField(
        value = email,
        onValueChange = { email = it },
        label = label,
        isPassword = false,
        onSubmit = { onSubmit(email) },
        modifier = modifier
    )
}

@Composable
private fun PasswordTextField(
    label : String,
    email : String,
    onSubmit : (email : String, password : String) -> Unit,
    modifier : Modifier = Modifier
) {
    var password by remember { mutableStateOf("") }

    AuthorizationTextField(
        value = password,
        onValueChange = { password = it },
        label = label,
        isPassword = true,
        onSubmit = { onSubmit(email, password) },
        modifier = modifier
    )
}

@Composable
private fun AuthorizationTextField(
    value : String,
    onValueChange : (String) -> Unit,
    label : String,
    isPassword : Boolean,
    onSubmit : () -> Unit,
    modifier : Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                label = { Text(label) },
                shape = RoundedCornerShape(6.dp),
                visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
                keyboardActions = KeyboardActions(
                    onDone = { onSubmit() })
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Row(
            modifier = Modifier.padding(top = 8.dp),
        ) {
            FilledIconButton(
                onClick = onSubmit,
                shape = RoundedCornerShape(12.dp),
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
}