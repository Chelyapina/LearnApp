package com.example.network.modelDto

import kotlinx.serialization.Serializable

@Serializable
data class UpdateSettingsRequestDto(
    val limitNew : Int? = null,
    val limitRepeat : Int? = null,
    val name : String? = null,
    val password : String? = null,
    val newPassword : String? = null
)