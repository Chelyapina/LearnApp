package com.example.network

import com.example.network.modelDto.AuthResponseDto
import com.example.network.modelDto.LoginRequestDto
import com.example.network.modelDto.WordCardDto
import com.example.network.modelDto.WordCompletedDto
import com.example.network.utils.NetworkConstants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ApiService {
    @POST(NetworkConstants.LOGIN)
    suspend fun login(@Body authRequest: LoginRequestDto): AuthResponseDto

    @GET(NetworkConstants.NEW_WORDS)
    suspend fun getLearnDeck(
        @Header("Authorization") token: String
    ): Response<List<WordCardDto>>

    @GET(NetworkConstants.REPEAT_WORDS)
    suspend fun getRepeatDeck(
        @Header("Authorization") token: String
    ): Response<List<WordCardDto>>

    @PATCH(NetworkConstants.COMPLETED_WORDS)
    suspend fun saveCompletedDeck(
        @Header("Authorization") token: String, @Body completedDeck : List<WordCompletedDto>
    ): Response<Unit>
}