package com.example.rightmovetechtask

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.rightmovetechtask.network.RightmoveApiService
import com.example.rightmovetechtask.network.RightmoveProperties
import com.example.rightmovetechtask.network.RightmoveProperty
import com.example.rightmovetechtask.usecases.CalculateAveragePropertyPrice
import com.example.rightmovetechtask.viewmodels.PropertyAveragePriceViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import java.math.BigDecimal

class PropertyAveragePriceViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val rightmoveProperty = RightmoveProperty(
        id = 5,
        price = 500000,
        bedrooms = 3,
        number = "55",
        address = "Straight Road",
        region = "Slough",
        postcode = "SL1 1PL",
        propertyType = "DETACHED"
    )

    private val rightmovePropertyZeroPrice = RightmoveProperty(
        id = 5,
        price = 0,
        bedrooms = 3,
        number = "55",
        address = "Straight Road",
        region = "Slough",
        postcode = "SL1 1PL",
        propertyType = "DETACHED"
    )
    private val rightmoveProperties = RightmoveProperties(listOf(rightmoveProperty))
    private val rightmovePropertiesZeroPrice =
        RightmoveProperties(listOf(rightmovePropertyZeroPrice))
    private val rightmovePropertiesEmpty = RightmoveProperties(listOf())

    private lateinit var propertyAveragePriceViewModel: PropertyAveragePriceViewModel
    private val mockApi = mock<RightmoveApiService>()
    private val calculateAveragePropertyPrice = CalculateAveragePropertyPrice()

    @Before
    fun setUp() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        propertyAveragePriceViewModel =
            PropertyAveragePriceViewModel(mockApi, calculateAveragePropertyPrice)
    }

    @Test
    fun `getListOfProperties calls getProperties once`() {
        whenever(mockApi.getProperties()).thenReturn(Single.just(rightmoveProperties))

        propertyAveragePriceViewModel.getListOfProperties()
        verify(mockApi, times(1)).getProperties()
    }

    @Test
    fun `getListOfProperties returns average property price`() {
        whenever(mockApi.getProperties()).thenReturn(Single.just(rightmoveProperties))

        propertyAveragePriceViewModel.getListOfProperties()
        assertEquals(
            500000.0.toBigDecimal(),
            propertyAveragePriceViewModel.averagePriceOfProperties.value
        )
    }

    @Test
    fun `getListOfProperties returns zero when all properties have zero price`() {
        whenever(mockApi.getProperties()).thenReturn(Single.just(rightmovePropertiesZeroPrice))

        propertyAveragePriceViewModel.getListOfProperties()

        assertEquals(
            0.0.toBigDecimal(),
            propertyAveragePriceViewModel.averagePriceOfProperties.value
        )
    }

    @Test
    fun `getListOfProperties returns error text when api returns an empty list of properties`() {
        whenever(mockApi.getProperties()).thenReturn(Single.just(rightmovePropertiesEmpty))

        propertyAveragePriceViewModel.getListOfProperties()

        assertNull(propertyAveragePriceViewModel.averagePriceOfProperties.value)

        assertEquals(
            "No properties found at this time",
            propertyAveragePriceViewModel.errorWithPropertiesListOrPrices.value
        )
    }
}