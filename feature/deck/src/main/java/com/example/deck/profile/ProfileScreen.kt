package com.example.deck.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.designsystem.components.appbar.AppBarState
import com.example.designsystem.components.appbar.CommonAppBar

@Composable
fun ProfileScreen(
    onBackClick : () -> Unit,
    modifier : Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CommonAppBar(
                modifier = Modifier.padding(16.dp), state = AppBarState.Back(
                    title = stringResource(id = R.string.profile_title)
                )
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
                    .clickable(onClick = {}), headlineContent = {
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
        }
    }
}