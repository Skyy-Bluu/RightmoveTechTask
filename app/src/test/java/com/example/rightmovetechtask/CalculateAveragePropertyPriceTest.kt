package com.example.rightmovetechtask

import com.example.rightmovetechtask.usecases.CalculateAveragePropertyPrice
import org.junit.Assert.assertEquals
import org.junit.Test

class CalculateAveragePropertyPriceTest {
    private val calculateAveragePropertyPrice = CalculateAveragePropertyPrice()

    private val listOfPrices = listOf(200000, 100000, 900000)
    private val listOfZeros = listOf(0, 0, 0)

    @Test
    fun `CalculateAveragePropertyPrice returns average of list of integers given`() {
        val averagePropertyPrice = calculateAveragePropertyPrice.execute(listOfPrices)
        assertEquals(400000.0.toBigDecimal(), averagePropertyPrice)
    }

    @Test
    fun `CalculateAveragePropertyPrice returns zero if list of integers given is all zeros`() {
        val averagePropertyPrice = calculateAveragePropertyPrice.execute(listOfZeros)
        assertEquals(0.0.toBigDecimal(), averagePropertyPrice)
    }
}