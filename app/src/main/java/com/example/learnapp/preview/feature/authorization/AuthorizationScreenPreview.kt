package com.example.learnapp.preview.feature.authorization

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.authorization.presentation.screen.AuthorizationScreenContent
import com.example.authorization.presentation.state.AuthScreenState
import com.example.designsystem.theme.LearnAppTheme

@Preview(showBackground = true)
@Composable
fun AuthorizationScreenPreview_LoginStateLight() {
    LearnAppTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            // Login State
            AuthorizationScreenContent(state = AuthScreenState.Login)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthorizationScreenPreview_PasswordStateLight() {
    LearnAppTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            // Password State
            AuthorizationScreenContent(state = AuthScreenState.Password(email = "example@example.com"))
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AuthorizationScreenPreview_LoginStateDark() {
    LearnAppTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            // Login State
            AuthorizationScreenContent(state = AuthScreenState.Login)
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AuthorizationScreenPreview_PasswordStateDark() {
    LearnAppTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            // Password State
            AuthorizationScreenContent(state = AuthScreenState.Password(email = "example@example.com"))
        }
    }
}