package com.example.network.modelDto

import kotlinx.serialization.Serializable

@Serializable
data class YearlyStatDto(
    val label : String,
    val value : Int
)