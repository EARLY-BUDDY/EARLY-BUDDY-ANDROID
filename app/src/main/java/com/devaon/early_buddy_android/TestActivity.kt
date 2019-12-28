package com.devaon.early_buddy_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_place_favorite.*
import kotlinx.android.synthetic.main.activity_test.*


class TestActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        act_place_favorite_floating_first.bringToFront();
        //makeChoiceController()
    }

//    private fun makeChoiceController()
//    {
//        fabChoice1.setOnClickListener {
//            Toast.makeText(this, "You select Choice1", Toast.LENGTH_SHORT).show()
//        }
//        fabChoice2.setOnClickListener {
//            Toast.makeText(this, "You select Choice2", Toast.LENGTH_SHORT).show()
//        }
//        fabChoice3.setOnClickListener {
//            Toast.makeText(this, "You select Choice3", Toast.LENGTH_SHORT).show()
//        }
//        fabChoice4.setOnClickListener {
//            Toast.makeText(this, "You select Choice4", Toast.LENGTH_SHORT).show()
//        }
//    }
}