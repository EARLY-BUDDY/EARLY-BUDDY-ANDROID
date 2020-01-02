package com.devaon.early_buddy_android.data.place

import com.google.gson.annotations.SerializedName

data class PlaceResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("data")
    val data: ArrayList<PlaceSearch>
)

data class PlaceSearch(
    @SerializedName("placeName")
    val placeName: String,
    @SerializedName("addressName")
    val addressName: String,
    @SerializedName("roadAddressName")
    val roadAddressName: String,
    @SerializedName("x")
    val x: Double,
    @SerializedName("y")
    val y: Double
)
