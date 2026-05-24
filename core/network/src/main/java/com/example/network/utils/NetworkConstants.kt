package com.example.network.utils

object NetworkConstants {
    const val BASE_URL = "http://217.71.129.139:6101/"

    const val TIMEOUT_SECONDS = 30L

    const val LOGIN = "auth/login"
    const val NEW_WORDS = "learning/words/new"
    const val REPEAT_WORDS = "learning/words/repeat"
    const val COMPLETED_WORDS = "learning/progress"
    const val SETTINGS = "/users/settings"
    const val STATISTICS_YEAR = "learning/statistics/year"
    const val STATISTICS_MONTH = "learning/statistics/month"
    const val DICTIONARY_CREATE = "dictionary/create"
    const val DICTIONARY_ADD_WORD = "dictionary/add"
    const val DICTIONARY_REMOVE_WORD = "dictionary/{dictionaryId}/word/{wordId}"
    const val DICTIONARY_DELETE = "dictionary/{dictionaryId}"
    const val DICTIONARY_SEARCH = "dictionary/search"
    const val DICTIONARY_GET_WORDS = "dictionary"
}