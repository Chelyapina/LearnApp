package com.example.network.di

import com.example.network.ApiService
import com.example.network.utils.NetworkConstants
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object NetworkModule {
    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideJson() : Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        explicitNulls = false
        coerceInputValues = true
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor() : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
//        TODO(
//                                    BuildConfig.DEBUG {
//                                HttpLoggingInterceptor.Level.BODY
//                            } else {
//                                HttpLoggingInterceptor.Level.NONE
//                            }
//        )
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor : HttpLoggingInterceptor,
    ) : OkHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder().addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json").build()
        chain.proceed(newRequest)
    }.addInterceptor(loggingInterceptor)
        .connectTimeout(NetworkConstants.TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(NetworkConstants.TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(NetworkConstants.TIMEOUT_SECONDS, TimeUnit.SECONDS).build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient : OkHttpClient, json : Json
    ) : Retrofit = Retrofit.Builder().baseUrl(NetworkConstants.BASE_URL).client(okHttpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType())).build()

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit : Retrofit) : ApiService =
            retrofit.create(ApiService::class.java)
}