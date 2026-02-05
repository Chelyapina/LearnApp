package com.example.designsystem.components.appbar

sealed class AppBarState {
    data class Back(
        val title : String = EMPTY_TITLE
    ) : AppBarState()
    data class TwoActions(
        val firstName: String,
        val onMenuClick: () -> Unit = {},
        val onAvatarClick: () -> Unit = {}
    ) : AppBarState()
    object Empty : AppBarState()

    companion object {
        const val EMPTY_TITLE = ""
    }
}