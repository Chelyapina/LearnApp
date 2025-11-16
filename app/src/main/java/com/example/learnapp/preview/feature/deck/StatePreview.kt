package com.example.learnapp.preview.feature.deck

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.deck.presentation.components.EmptyDeck
import com.example.deck.presentation.state.ErrorState
import com.example.deck.presentation.state.LoadingState
import com.example.designsystem.theme.LearnAppTheme
import com.example.learnapp.R

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ErrorStatePreview() {
    LearnAppTheme {
        ErrorState(
            errorMessage = stringResource(R.string.error_state_preview), onRetry = {})
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoadingStatePreview() {
    LearnAppTheme {
        LoadingState()
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