package com.example.learnapp

import android.content.res.Configuration
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.LearnAppTheme

@VisibleForTesting
@Composable
fun ThemePreviewScreen() {
    LearnAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    stringResource(R.string.display_large),
                    style = MaterialTheme.typography.displayLarge
                )
                Text(
                    stringResource(R.string.display_medium),
                    style = MaterialTheme.typography.displayMedium
                )
                Text(
                    stringResource(R.string.display_small),
                    style = MaterialTheme.typography.displaySmall
                )
                Text(
                    stringResource(R.string.title_small),
                    style = MaterialTheme.typography.titleSmall
                )

                FilledIconButton(
                    onClick = {},
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.size(48.dp),
                ) {
                    Icon(
                        Icons.Default.Add, contentDescription = stringResource(R.string.add)
                    )
                }

                ListItem(headlineContent = {
                    Text(stringResource(R.string.headline))
                }, supportingContent = {
                    Text(stringResource(R.string.supporting_text))
                })

                var login by remember { mutableStateOf("example@example.com") }
                OutlinedTextField(
                    value = login, onValueChange = { newValue -> login = newValue },
                    label = { Text(stringResource(R.string.login_hint)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = MaterialTheme.shapes.medium
                        ), contentAlignment = Alignment.Center
                ) {
                    Text(
                        "PrimaryContainer", color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                            shape = MaterialTheme.shapes.medium
                        ), contentAlignment = Alignment.Center
                ) {
                    Text(
                        "PrimaryContainer (alpha 0.5f)",
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
}

@Preview(name = "Light Theme", showBackground = true)
@Preview(name = "Dark Theme", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ThemePreviews() {
    LearnAppTheme {
        ThemePreviewScreen()
    }
}