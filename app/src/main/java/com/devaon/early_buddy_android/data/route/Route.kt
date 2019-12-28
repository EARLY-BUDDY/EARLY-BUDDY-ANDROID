package com.devaon.early_buddy_android.data.route

import com.google.gson.annotations.SerializedName

data class Route(
    @SerializedName("startAddress")
    val startAddress: String,
    @SerializedName("endAddress")
    val endAddress: String,
    var clicked: Boolean,
    val path: ArrayList<String>

)