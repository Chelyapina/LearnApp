package com.example.deck.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.deck.R
import com.example.designsystem.theme.ActionButtonColors
import com.example.designsystem.theme.ActionButtonTheme

@Composable
fun CardActionsRow(
    onForget : () -> Unit, onKnow : () -> Unit, modifier : Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ActionButton(
            text = stringResource(R.string.dont_remember),
            icon = Icons.Outlined.Close,
            colors = ActionButtonTheme.forgetColors(),
            onClick = onForget,
            modifier = Modifier.Companion.weight(1f)
        )

        Spacer(modifier = Modifier.width(24.dp))

        ActionButton(
            text = stringResource(R.string.remember),
            icon = Icons.Outlined.Check,
            colors = ActionButtonTheme.rememberColors(),
            onClick = onKnow,
            modifier = Modifier.Companion.weight(1f)
        )
    }
}

@Composable
private fun ActionButton(
    text : String,
    icon : ImageVector,
    colors : ActionButtonColors,
    onClick : () -> Unit,
    modifier : Modifier = Modifier
) {
    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier.size(72.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = colors.container, contentColor = colors.icon
            ),
        ) {
            Icon(
                imageVector = icon, contentDescription = text, modifier = Modifier.size(48.dp)
            )
        }
    }
}