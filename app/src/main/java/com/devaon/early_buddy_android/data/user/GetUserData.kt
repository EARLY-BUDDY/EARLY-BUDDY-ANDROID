package com.devaon.early_buddy_android.data.user

import com.google.gson.annotations.SerializedName

data class GetUserData(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("userPw")
    val userPw: String?
)