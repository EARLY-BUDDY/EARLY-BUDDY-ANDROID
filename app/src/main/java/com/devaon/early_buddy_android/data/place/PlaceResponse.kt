package com.devaon.early_buddy_android.data.place

import com.google.gson.annotations.SerializedName

data class PlaceResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("data")
    val data: Int
)

data class PlaceSearch(
    @SerializedName("addressName")     // 1 -> 지하철, 2 -> 버스, 3 -> 버스+지하철
    val addressName: String,
    @SerializedName("roadAddressName")
    val roadAddressName: String,
    @SerializedName("x")
    val x: Double,
    @SerializedName("y")
    val y: Double
)
