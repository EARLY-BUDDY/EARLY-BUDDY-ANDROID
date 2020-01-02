package com.devaon.early_buddy_android.data.db

import android.app.Application

class App : Application() {

    companion object {
        lateinit var prefs : SharedPreferenceController
    }

    override fun onCreate() {
        prefs = SharedPreferenceController(applicationContext)
        super.onCreate()
    }
}