package com.example.learnapp.preview.feature.deck

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.deck.presentation.components.EmptyDeck
import com.example.deck.presentation.screen.ErrorDeckScreen
import com.example.designsystem.components.loading.LoadingScreen
import com.example.designsystem.theme.LearnAppTheme
import com.example.learnapp.R

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ErrorStatePreview() {
    LearnAppTheme {
        ErrorDeckScreen(
            errorMessage = stringResource(R.string.error_state_preview), onRetry = {})
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoadingStatePreview() {
    LearnAppTheme {
        LoadingScreen(text = R.string.loading_state_preview)
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun EmptyDeckPreview() {
    LearnAppTheme {
        EmptyDeck(
            onReset = {})
    }
}