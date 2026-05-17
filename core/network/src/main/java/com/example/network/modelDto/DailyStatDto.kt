package com.example.network.modelDto

import kotlinx.serialization.Serializable

@Serializable
data class DailyStatDto(
    val label : String,
    val value : Int
)