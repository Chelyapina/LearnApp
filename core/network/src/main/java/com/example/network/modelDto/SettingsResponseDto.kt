package com.example.network.modelDto

import kotlinx.serialization.Serializable

@Serializable
data class SettingsResponseDto(
    val limitNew : Int? = null,
    val limitRepeat : Int? = null,
    val name : String? = null
)