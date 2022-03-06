package com.example.rightmovetechtask.usecases

import java.math.BigDecimal

class CalculateAveragePropertyPrice {

    fun execute(listOfPropertyPrices: List<Int>): BigDecimal =
        listOfPropertyPrices.average().toBigDecimal()
}