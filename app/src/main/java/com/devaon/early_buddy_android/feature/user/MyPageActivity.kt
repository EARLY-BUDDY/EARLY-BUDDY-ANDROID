package com.devaon.early_buddy_android.feature.user

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.devaon.early_buddy_android.R
import kotlinx.android.synthetic.main.activity_mypage.*

class MyPageActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        act_my_back.setOnClickListener {
            finish()
        }

    }
}