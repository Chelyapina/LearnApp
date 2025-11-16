package com.example.deck.domain.businesslogic

import javax.inject.Inject

class WordProgressCalculator @Inject constructor() {
    fun calculateNewStatus(currentStatus : Int, isKnown : Boolean) : Int {
        return if (isKnown) {
            minOf(currentStatus + 1, 7)
        } else {
            maxOf(currentStatus - 1, 0)
        }
    }
}