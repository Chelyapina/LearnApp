package com.example.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

object ActionButtonTheme {

    @Composable
    fun rememberColors() : ActionButtonColors {
        val isDark = isSystemInDarkTheme()

        return remember(isDark) {
            if (isDark) {
                ActionButtonColors(
                    container = SuccessGreenDark,
                    icon = SuccessGreenIconDark
                )
            } else {
                ActionButtonColors(
                    container = SuccessGreenLight,
                    icon = SuccessGreenIconLight
                )
            }
        }
    }

    @Composable
    fun forgetColors() : ActionButtonColors {
        val isDark = isSystemInDarkTheme()

        return remember(isDark) {
            if (isDark) {
                ActionButtonColors(
                    container = ErrorRedDark,
                    icon = ErrorRedIconDark
                )
            } else {
                ActionButtonColors(
                    container = ErrorRedLight,
                    icon = ErrorRedIconLight
                )
            }
        }
    }
}