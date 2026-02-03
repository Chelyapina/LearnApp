package com.example.authorization.domain.entity

@JvmInline
value class Token(private val token : String) {
    fun getValue() : String = token
    override fun toString() : String = "TokenEntity(***)"

    companion object {
        val EMPTY = Token("")
    }
}