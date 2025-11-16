package com.example.designsystem.components.appbar

sealed class AppBarState {
    object Back : AppBarState()
    data class TwoActions(
        val firstName: String,
        val onMenuClick: () -> Unit = {},
        val onAvatarClick: () -> Unit = {}
    ) : AppBarState()
    object Empty : AppBarState()
}