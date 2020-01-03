package com.devaon.early_buddy_android

import android.app.Activity
import android.app.Application
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.lottie.LottieAnimationView
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

    var progressDialog: AppCompatDialog? = null
    lateinit var earlyBuddyApplication: EarlyBuddyApplication

    override fun onCreate() {
        super.onCreate()

        instance = this
        globalApplication = this

        prefs = SharedPreferenceController(applicationContext)
        super.onCreate()
    }

    fun getInstance(): EarlyBuddyApplication {
        return earlyBuddyApplication; }

    fun progressON(activity: Activity?, message: String) {

        if (activity == null || activity.isFinishing) {
            return
        }

        if (progressDialog != null && progressDialog!!.isShowing) {
            progressSET(message)
        } else {
            progressDialog = AppCompatDialog(activity)
            progressDialog!!.setCancelable(false)
            progressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            progressDialog!!.setContentView(R.layout.activity_loading)
            progressDialog!!.show()
        }


        val loadingAnimation = progressDialog!!.findViewById(R.id.act_loading_av) as LottieAnimationView?
        val frameAnimation = loadingAnimation!!.background as AnimationDrawable
        loadingAnimation.post { frameAnimation.start() }

    }

    fun progressSET(message: String) {

        if (progressDialog == null || !progressDialog!!.isShowing) {
            return
        }
    }

    fun progressOFF() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
    }
}