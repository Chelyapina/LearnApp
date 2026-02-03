package com.example.network.modelDto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponseDto(
    @SerialName("token")
    val token: String,

    @SerialName("username")
    val username: String
)