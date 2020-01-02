package com.devaon.early_buddy_android.data.db

import android.content.Context
import android.content.SharedPreferences

object SharedPreferenceController {

    val MY_ACCOUNT = "unique_string"
    val REFRESH_TOKEN = "refresh_token"
    val ACCESS_TOKEN = "access_token"

    fun setUserToken(ctx: Context, u_token: String?) {

        val preference: SharedPreferences = ctx.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)

        val editor: SharedPreferences.Editor = preference.edit()
        editor.putString("token", u_token)
        editor.commit()
    }

    fun getUserToken(ctx: Context): String {
        val preference: SharedPreferences = ctx.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return preference.getString("token", "") ?: ""
    }

    fun clearUserToken(ctx: Context) {
        val preference: SharedPreferences = ctx.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = preference.edit()
        editor.clear()
        editor.commit()
    }

    fun setAccessToekn(ctx : Context, accessToken : String) {
        val preferences : SharedPreferences = ctx.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(ACCESS_TOKEN, accessToken)
        editor.commit()
    }

    fun getAccessToken(ctx : Context) : String{
        val preference : SharedPreferences = ctx.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return preference.getString("token", "")!!

    }

    fun setRefreshToken(ctx : Context, refreshToken : String) {
        val preference : SharedPreferences = ctx.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor = preference.edit()
        editor.putString(REFRESH_TOKEN, refreshToken)
        editor.commit()
    }

    fun getRefreshToken(ctx : Context) : String{
        val preference = ctx.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return preference.getString(REFRESH_TOKEN, "")!!
    }


}