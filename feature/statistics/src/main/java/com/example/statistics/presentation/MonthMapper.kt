package com.example.statistics.presentation

object MonthMapper {

    private val ENGLISH_SHORT_NAMES = listOf(
        "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    )

    private val RUSSIAN_FULL_NAMES = listOf(
        "Январь",
        "Февраль",
        "Март",
        "Апрель",
        "Май",
        "Июнь",
        "Июль",
        "Август",
        "Сентябрь",
        "Октябрь",
        "Ноябрь",
        "Декабрь"
    )

    fun toRussianFullName(englishName : String) : String {
        val monthNumber = ENGLISH_SHORT_NAMES.indexOf(englishName) + 1
        return RUSSIAN_FULL_NAMES.getOrElse(monthNumber - 1) { "" }
    }

    fun toNumber(englishName : String) : Int {
        return ENGLISH_SHORT_NAMES.indexOf(englishName) + 1
    }

    fun toUINumber(englishName : String) : String {
        val monthNumber = ENGLISH_SHORT_NAMES.indexOf(englishName) + 1
        return monthNumber.toString().padStart(2, '0')
    }

    fun toEnglishName(monthNumber : Int) : String {
        return ENGLISH_SHORT_NAMES.getOrElse(monthNumber - 1) { "" }
    }
}