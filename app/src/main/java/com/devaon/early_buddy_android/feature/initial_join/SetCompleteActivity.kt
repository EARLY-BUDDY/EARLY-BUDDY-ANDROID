package com.devaon.early_buddy_android.feature.initial_join

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.feature.home.HomeActivity
import kotlinx.android.synthetic.main.activity_set_complete.*
import kotlinx.android.synthetic.main.activity_set_nickname.*
import java.util.regex.Pattern

class SetCompleteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_complete)

        set_complete_cl_start?.setOnClickListener {
            val intent = Intent(this@SetCompleteActivity, HomeActivity::class.java)
            startActivity(intent)
        }

    }
}