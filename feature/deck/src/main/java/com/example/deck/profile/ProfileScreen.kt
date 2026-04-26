package com.example.deck.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.deck.R
import com.example.deck.presentation.state.DeckEvent
import com.example.deck.presentation.viewmodel.DeckViewModel
import com.example.designsystem.components.appbar.AppBarState
import com.example.designsystem.components.appbar.CommonAppBar

@Composable
fun ProfileScreen(
    viewModel: DeckViewModel,
    onBackClick : () -> Unit, onSettingsClick : () -> Unit,
    modifier : Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CommonAppBar(
                modifier = Modifier.padding(16.dp), state = AppBarState.Back(
                    title = stringResource(id = R.string.profile_title)
                ),
                onBackClick = onBackClick
            )
        }, modifier = modifier.systemBarsPadding()
    ) { paddingValues ->
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(paddingValues)
                .padding(horizontal = 26.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            ListItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .clickable(onClick = onSettingsClick),
                headlineContent = {
                Text(text = stringResource(id = R.string.settings))
            }, supportingContent = {
                Text(text = stringResource(id = R.string.settings_description))
            })
            ListItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .clickable(onClick = {}), headlineContent = {
                Text(text = stringResource(id = R.string.dictionaries))
            }, supportingContent = {
                Text(text = stringResource(id = R.string.dictionaries_description))
            })

            Button(
                onClick = { viewModel.handleEvent(DeckEvent.Logout) },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(0.8f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(R.string.profile_exit))
            }
        }
    }
}