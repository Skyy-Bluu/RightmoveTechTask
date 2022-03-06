package com.example.rightmovetechtask.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rightmovetechtask.network.RightmoveApiService
import com.example.rightmovetechtask.usecases.CalculateAveragePropertyPrice
import io.reactivex.rxjava3.schedulers.Schedulers
import java.math.BigDecimal

class PropertyAveragePriceViewModel(
    private val rightmoveApiService: RightmoveApiService,
    private val calculateAveragePropertyPrice: CalculateAveragePropertyPrice
) : ViewModel() {
    private val _averagePriceOfProperties = MutableLiveData<BigDecimal>()
    private val _errorWithPropertiesListOrPrices = MutableLiveData<String>()

    val averagePriceOfProperties: LiveData<BigDecimal>
        get() = _averagePriceOfProperties

    val errorWithPropertiesListOrPrices: LiveData<String>
        get() = _errorWithPropertiesListOrPrices

    @SuppressLint("LongLogTag")
    fun getListOfProperties() {
        rightmoveApiService.getProperties().subscribeOn(Schedulers.io()).subscribe { value, error ->
            value?.let {
                val prices = it.properties
                    .map { properties -> properties.price }
                handleApiResponse(prices)
            }
            error?.let {
                Log.e(LOG_TAG, "Failure: ${it.message}")
            }
        }
    }

    private fun handleApiResponse(prices: List<Int>) {
        if (prices.isNullOrEmpty()) {
            _errorWithPropertiesListOrPrices.postValue(NO_PROPERTIES_TEXT)
        } else {
            try {
                _averagePriceOfProperties.postValue(calculateAveragePropertyPrice.execute(prices))
            } catch (exception: Exception) {
                _errorWithPropertiesListOrPrices.postValue(NO_AVERAGE_PRICE_TEXT)
            }
        }
    }

    companion object {
        private const val LOG_TAG = "PropertyAveragePriceViewModel"
        private const val NO_PROPERTIES_TEXT = "No properties found at this time"
        private const val NO_AVERAGE_PRICE_TEXT = "No property prices found at this time"
    }
}