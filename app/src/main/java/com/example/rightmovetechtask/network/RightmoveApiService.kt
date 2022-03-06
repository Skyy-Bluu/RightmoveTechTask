package com.example.rightmovetechtask.network

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface RightmoveApiService {

    @GET("properties.json")
    fun getProperties(): Single<RightmoveProperties>
}