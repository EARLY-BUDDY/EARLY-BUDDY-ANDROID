package com.devaon.early_buddy_android.data.place

import com.google.gson.annotations.SerializedName

data class GetPlaceData(
    @SerializedName("address")
    val address: String?,
    @SerializedName("addressDetail")
    val addressDetail: String?,
    @SerializedName("loadAddress")
    val loadAddress: String?
)