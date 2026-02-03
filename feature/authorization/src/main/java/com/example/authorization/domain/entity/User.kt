package com.example.authorization.domain.entity

data class User(
    val name : String, private val token : Token = Token.EMPTY
) {
    fun getToken() : Token = token
    override fun toString() : String = "User(name='$name', token=$token)"
}