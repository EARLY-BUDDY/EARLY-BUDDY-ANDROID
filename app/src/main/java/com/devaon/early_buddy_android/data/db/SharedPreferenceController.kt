package com.devaon.early_buddy_android.data.db

import android.content.Context
import android.content.SharedPreferences


class SharedPreferenceController(context: Context) {

    val PREFS_JWT = "prefs"
    val PREF_KEY_JWT = "jwt"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_JWT, 0)

    var jwt: String
        get() = prefs.getString(PREF_KEY_JWT, "").toString()
        set(value) = prefs.edit().putString(PREF_KEY_JWT, value).apply()

  /*
  val PREFS_FILENAME = "prefs"
    val PREF_KEY_MY_EDITTEXT = "myEditText"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)
    *//* 파일 이름과 EditText를 저장할 Key 값을 만들고 prefs 인스턴스 초기화 *//*

    var myEditText: String
        get() = prefs.getString(PREF_KEY_MY_EDITTEXT, "")
        set(value) = prefs.edit().putString(PREF_KEY_MY_EDITTEXT, value).apply()
        */



}
   /* fun setAuthorization(context: Context, authorization : String)
    {
        val pref = context.getSharedPreferences(PREFS_NICKNAME, Context.MODE_PRIVATE) //현재 내 기기에서만 볼수 있는 데이터
        val editor = pref.edit()
        editor.putString(myAuth, authorization)
        editor.apply()
    }

    fun getAuthorization(context: Context) : String {
        val pref = context.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE) //현재 내 기기에서만 볼수 있는 데이터
        return pref.getString(myAuth, "")!!
    }
//
//    fun setIsLogout(context: Context, v : Boolean)
//    {
//        val pref = context.getSharedPreferences(isLogout, Context.MODE_PRIVATE) //현재 내 기기에서만 볼수 있는 데이터
//        val editor = pref.edit()
//        editor.putBoolean(isLogout, v)
//        editor.apply()
//    }
//
//    fun getIsLogout(context: Context) : Boolean {
//        val pref = context.getSharedPreferences(isLogout, Context.MODE_PRIVATE) //현재 내 기기에서만 볼수 있는 데이터
//        return pref.getBoolean(isLogout, false)
//    }

}
*/