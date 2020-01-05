package com.devaon.early_buddy_android.data.user

import com.google.gson.annotations.SerializedName

//회원가입 응답메시지
//1) 회원가입 성공 true data 1
//2) 중복아이디 실패 false message
data class UserResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("data")
    val idx: Int,
    @SerializedName("message")
    val message: String
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
    @SerializedName("deviceToken")
    val deviceToken: String
)

data class UserNickname(
    @SerializedName("userName")
    val userName: String
)

data class UserSigninResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("data")
    val data: UserSigninData
)

data class UserSigninData(
    @SerializedName("jwt")
    val jwt: String,
    @SerializedName("userIdx")
    val Idx: Int,
    @SerializedName("userName")
    val userName: String
)



