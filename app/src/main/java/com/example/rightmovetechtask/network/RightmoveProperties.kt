package com.example.rightmovetechtask.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RightmoveProperties(val properties: List<RightmoveProperty>) : Parcelable

@Parcelize
data class RightmoveProperty(
    val id: Int,
    val price: Int,
    val bedrooms: Int,
    val number: String,
    val address: String,
    val region: String,
    val postcode: String,
    // This could be an enum depending on what other property types there are
    val propertyType: String
) : Parcelable