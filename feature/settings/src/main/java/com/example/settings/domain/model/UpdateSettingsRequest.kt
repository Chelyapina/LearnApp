package com.example.settings.domain.model

data class UpdateSettingsRequest(
    val limitNewWords : Int? = null,
    val limitWordsForRepeat : Int? = null,
    val oldPassword : String,
    val newPassword : String? = null
)