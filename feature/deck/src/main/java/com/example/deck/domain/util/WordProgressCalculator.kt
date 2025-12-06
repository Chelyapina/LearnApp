package com.example.deck.domain.util

object WordProgressCalculator {
    private const val MAX_STATUS = 7
    private const val MIN_STATUS = 0

    fun calculateNewStatus(currentStatus : Int, isKnown : Boolean) : Int {
        return if (isKnown) {
            minOf(currentStatus + 1, MAX_STATUS)
        } else {
            maxOf(currentStatus - 1, MIN_STATUS)
        }
    }
}