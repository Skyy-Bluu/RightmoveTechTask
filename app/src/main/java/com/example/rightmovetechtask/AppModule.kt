package com.example.rightmovetechtask

import com.example.rightmovetechtask.network.RightmoveApiService
import com.example.rightmovetechtask.usecases.CalculateAveragePropertyPrice
import com.example.rightmovetechtask.viewmodels.PropertyAveragePriceViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object AppModule {
    private const val BASE_URL =
        "https://raw.githubusercontent.com/rightmove/Code-Challenge-Android/master/"
    val appModule = module {

        single<Moshi> {
            Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        }

        single<Retrofit> {
            val rxJavaAdapterFactory = RxJava3CallAdapterFactory.createSynchronous()

            Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(get()))
                .addCallAdapterFactory(rxJavaAdapterFactory)
                .baseUrl(BASE_URL)
                .build()
        }

        single<RightmoveApiService> {
            get<Retrofit>().create(RightmoveApiService::class.java)
        }

        factory { CalculateAveragePropertyPrice() }

        viewModel { PropertyAveragePriceViewModel(get(), get()) }
    }

}