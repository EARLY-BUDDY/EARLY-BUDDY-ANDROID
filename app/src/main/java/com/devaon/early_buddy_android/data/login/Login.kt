package com.devaon.early_buddy_android.data.login

import android.content.Context

object Login {
    private const val LOGIN_KEY = "id"
    private const val USER_KEY = "user"

    fun getUser(context: Context): String {
        val sharedPreferences = context.getSharedPreferences(LOGIN_KEY, Context.MODE_PRIVATE)
        return sharedPreferences.getString(USER_KEY, "") ?: ""
    }

    fun setUser(context: Context, user: String) {
        val sharedPreferences = context.getSharedPreferences(LOGIN_KEY, Context.MODE_PRIVATE)
        sharedPreferences
            .edit()
            .putString(USER_KEY, user)
            .apply()
    }

    fun clearUser(context: Context) {
        val sharedPreferences = context.getSharedPreferences(LOGIN_KEY, Context.MODE_PRIVATE)
        sharedPreferences
            .edit()
            .clear()
            .apply()
    }
}

