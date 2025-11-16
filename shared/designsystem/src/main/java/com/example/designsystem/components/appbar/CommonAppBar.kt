package com.example.designsystem.components.appbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.designsystem.R
import com.example.designsystem.theme.customOnBackgroundColor

@Composable
fun CommonAppBar(
    modifier : Modifier = Modifier,
    state : AppBarState = AppBarState.Empty,
    onBackClick : () -> Unit = {},
    backgroundColor : Color = MaterialTheme.colorScheme.background,
    contentColor : Color = MaterialTheme.colorScheme.customOnBackgroundColor
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(backgroundColor),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        when (state) {
            is AppBarState.Back -> {
                AppBarIconButton(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.description_icon_back),
                    onClick = onBackClick,
                    tint = contentColor
                )
            }

            is AppBarState.TwoActions -> {
                AppBarIconButton(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = stringResource(R.string.description_icon_menu),
                    onClick = state.onMenuClick,
                    tint = contentColor
                )
                InitialAvatarButton(
                    onClick = state.onAvatarClick,
                    firstName = state.firstName,
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    textColor = contentColor
                )
            }

            AppBarState.Empty -> {}
        }
    }
}

@Composable
private fun InitialAvatarButton(
    onClick : () -> Unit,
    firstName : String,
    modifier : Modifier = Modifier,
    size : Dp = 48.dp,
    backgroundColor : Color = MaterialTheme.colorScheme.primary,
    textColor : Color = MaterialTheme.colorScheme.onPrimary,
) {
    val initial =
            firstName.firstOrNull()?.uppercaseChar() ?: stringResource(R.string.default_initial)

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable { onClick() }, contentAlignment = Alignment.Center
    ) {
        Text(
            text = initial.toString(),
            color = textColor,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun AppBarIconButton(
    imageVector : ImageVector,
    contentDescription : String,
    onClick : () -> Unit,
    tint : Color = MaterialTheme.colorScheme.onPrimary
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = imageVector, contentDescription = contentDescription, tint = tint
        )
    }
}