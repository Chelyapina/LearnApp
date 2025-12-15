package com.example.authorization.data.converter

import com.example.authorization.domain.entity.Token
import com.example.authorization.domain.entity.User
import com.example.network.modelDto.AuthResponseDto

object TokenConverter {
    fun convertToDomain(dto : AuthResponseDto) : User = User(
        name = dto.username, token = Token(dto.token)
    )
}