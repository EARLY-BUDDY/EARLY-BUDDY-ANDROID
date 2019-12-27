package com.devaon.early_buddy_android.feature

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.devaon.early_buddy_android.PlaceFavoriteActivity
import com.devaon.early_buddy_android.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        home.setOnClickListener{
            val intent = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        setnickname.setOnClickListener{
            val intent = Intent(this@MainActivity, SetNicknameActivity::class.java)
            startActivity(intent)
        }
    }
}
