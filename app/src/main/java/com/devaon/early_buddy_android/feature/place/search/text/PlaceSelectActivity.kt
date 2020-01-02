package com.devaon.early_buddy_android.feature.place.search.text

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.devaon.early_buddy_android.R
import kotlinx.android.synthetic.main.activity_place_select.*

class PlaceSelectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_select)

        act_place_select_cl_department.setOnClickListener {
            /*val intent = Intent(this@PlaceSelectActivity, PlaceDirectionsActivity::class.java)*/
            val intent = Intent(this@PlaceSelectActivity, PlaceDirectionsActivity::class.java)
            startActivity(intent)
        }


        act_place_select_et_department.setOnClickListener {
            /*val intent = Intent(this@PlaceSelectActivity, PlaceDirectionsActivity::class.java)*/
            val intent = Intent(this@PlaceSelectActivity, PlaceDirectionsActivity::class.java)
            startActivity(intent)
        }

        act_place_select_cl_arrival.setOnClickListener {
            val intent = Intent(this@PlaceSelectActivity, PlaceDirectionsActivity::class.java)
            startActivity(intent)
        }

        act_place_select_et_arrival.setOnClickListener {
            val intent = Intent(this@PlaceSelectActivity, PlaceDirectionsActivity::class.java)
            startActivity(intent)
        }
    }
}
