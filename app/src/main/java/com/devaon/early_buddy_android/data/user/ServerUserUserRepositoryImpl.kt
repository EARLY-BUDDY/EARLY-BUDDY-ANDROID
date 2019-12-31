package com.devaon.early_buddy_android.data.user

import retrofit2.Call

interface ServerUserUserRepositoryImpl{
    fun getUser(id: String): Call<GetUserData>

}