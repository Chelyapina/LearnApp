package com.example.network

import com.example.network.modelDto.AddWordToDictionaryRequestDto
import com.example.network.modelDto.AuthResponseDto
import com.example.network.modelDto.CreateDictionaryRequestDto
import com.example.network.modelDto.DailyStatDto
import com.example.network.modelDto.DictionaryResponseDto
import com.example.network.modelDto.LoginRequestDto
import com.example.network.modelDto.SearchWordResponseDto
import com.example.network.modelDto.SettingsResponseDto
import com.example.network.modelDto.UpdateSettingsRequestDto
import com.example.network.modelDto.WordCardDto
import com.example.network.modelDto.WordCompletedDto
import com.example.network.modelDto.YearlyStatDto
import com.example.network.utils.NetworkConstants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET(NetworkConstants.SETTINGS)
    suspend fun getSettings(
        @Header("Authorization") token : String
    ) : Response<SettingsResponseDto>

    @PATCH(NetworkConstants.SETTINGS)
    suspend fun updateSettings(
        @Header("Authorization") token : String, @Body request : UpdateSettingsRequestDto
    ) : Response<Unit>

    @GET(NetworkConstants.STATISTICS_YEAR)
    suspend fun getYearlyStatistics(
        @Header("Authorization") token : String, @Query("year") year : Int
    ) : Response<List<YearlyStatDto>>

    @GET(NetworkConstants.STATISTICS_MONTH)
    suspend fun getMonthlyStatistics(
        @Header("Authorization") token : String,
        @Query("year") year : Int,
        @Query("month") month : Int
    ) : Response<List<DailyStatDto>>

    @POST(NetworkConstants.DICTIONARY_CREATE)
    suspend fun createDictionary(
        @Header("Authorization") token: String,
        @Body request: CreateDictionaryRequestDto
    ): Response<Unit>

    @POST(NetworkConstants.DICTIONARY_ADD_WORD)
    suspend fun addWordToDictionary(
        @Header("Authorization") token: String,
        @Body request: AddWordToDictionaryRequestDto
    ): Response<Unit>

    @DELETE(NetworkConstants.DICTIONARY_REMOVE_WORD)
    suspend fun removeWordFromDictionary(
        @Header("Authorization") token: String,
        @Path("dictionaryId") dictionaryId: Int,
        @Path("wordId") wordId: Int
    ): Response<Unit>

    @DELETE(NetworkConstants.DICTIONARY_DELETE)
    suspend fun deleteDictionary(
        @Header("Authorization") token: String,
        @Path("dictionaryId") dictionaryId: Int
    ): Response<Unit>

    @GET(NetworkConstants.DICTIONARY_SEARCH)
    suspend fun searchWords(
        @Header("Authorization") token: String,
        @Query("prefix") prefix: String
    ): Response<List<SearchWordResponseDto>>

    @GET(NetworkConstants.DICTIONARY_GET_WORDS)
    suspend fun getDictionaryWords(
        @Header("Authorization") token: String
    ): Response<List<DictionaryResponseDto>>
}