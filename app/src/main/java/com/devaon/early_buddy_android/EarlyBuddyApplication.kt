package com.devaon.early_buddy_android

import android.app.Application
import com.devaon.early_buddy_android.data.db.SharedPreferenceController

class EarlyBuddyApplication : Application() {

    companion object{
        lateinit var  globalApplication: EarlyBuddyApplication

        lateinit var prefs : SharedPreferenceController

        lateinit var instance : EarlyBuddyApplication

        fun getGlobalApplicationContext(): EarlyBuddyApplication {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        globalApplication = this

        prefs = SharedPreferenceController(applicationContext)
        super.onCreate()

    }
}