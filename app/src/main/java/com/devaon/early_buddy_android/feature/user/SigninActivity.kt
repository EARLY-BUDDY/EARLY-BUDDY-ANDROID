package com.devaon.early_buddy_android.feature.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.login.Login
import com.devaon.early_buddy_android.feature.home.HomeActivity

class SigninActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        val id = Login.getUser(this)
        if (id.isNotEmpty()) {
            HomeActivity(id)
            finish()
        }

        Log.d("budy", "자동로그인")
    }


    private fun HomeActivity(id: String) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("login", id)

        startActivity(intent)
    }
}
