package com.example.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = BaseYellow,
    onPrimary = DarkGray,
    primaryContainer = AmberContainer,
    onPrimaryContainer = DarkSurfaceGray,
    background = DarkGray,
    onBackground = White,
    surface = DarkSurfaceGray,
    onSurface = White,
    onSurfaceVariant = MediumGray,
    outline = MediumGray
)

private val LightColorScheme = lightColorScheme(
    primary = BaseYellow,
    onPrimary = DarkGray,
    primaryContainer = AmberContainer,
    onPrimaryContainer = DarkSurfaceGray,
    background = White,
    onBackground = DarkGray,
    surface = LightSurfaceGray,
    onSurface = DarkGray,
    onSurfaceVariant = MediumGray,
    outline = MediumGray
)

@Composable
fun LearnAppTheme(
    darkTheme : Boolean = isSystemInDarkTheme(), content : @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme, typography = Typography, content = content
    )
}