package com.example.learnapp.preview

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.learnapp.R
import com.example.designsystem.components.appbar.AppBarState
import com.example.designsystem.components.appbar.CommonAppBar
import com.example.designsystem.theme.LearnAppTheme

@Composable
private fun PreviewDivider() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
    )
}

@Preview(showBackground = true)
@Composable
fun CommonAppBarPreview_AllStatesLight() {
    LearnAppTheme {
        Column {
            // Back State
            CommonAppBar(state = AppBarState.Back)
            PreviewDivider()

            // Two Actions State
            CommonAppBar(state = AppBarState.TwoActions(firstName = stringResource(R.string.preview_firstname)))
            PreviewDivider()

            // Empty State
            CommonAppBar(state = AppBarState.Empty)
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CommonAppBarPreview_AllStatesDark() {
    LearnAppTheme {
        Column {
            // Back State
            CommonAppBar(state = AppBarState.Back)
            PreviewDivider()

            // Two Actions State
            CommonAppBar(state = AppBarState.TwoActions(firstName = stringResource(R.string.preview_firstname)))
            PreviewDivider()

            // Empty State
            CommonAppBar(state = AppBarState.Empty)
        }
    }
}