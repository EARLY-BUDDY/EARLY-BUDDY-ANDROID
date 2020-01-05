package com.devaon.early_buddy_android.data.schedule

import com.google.gson.annotations.SerializedName


data class PostScheduleData(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("data")
    val data: Int
)