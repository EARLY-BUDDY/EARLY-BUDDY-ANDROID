package com.devaon.early_buddy_android.data.user

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("data")
    val idx: Int
)

data class NickNameResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("data")
    val nickName: String
)

data class UserSignup(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("userPw")
    val userPw: String
)

data class UserSignin(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("userPw")
    val userPw: String,
    @SerializedName("userIdx")
    val userIdx: String
)

data class UserNickname(
    @SerializedName("userName")
    val userName: String
)

data class UserSigninResponse(
    @SerializedName("jwt")
    val jwt: String
)

