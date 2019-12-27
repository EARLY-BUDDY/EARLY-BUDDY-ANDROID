package com.devaon.early_buddy_android.feature

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.feature.route.RouteActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        changeColor()
        intent()
    }

    private fun changeColor() {
        act_home_tv_bus_number.setOnClickListener {
            when (val background = act_home_tv_bus_number.getBackground()) {
                is ShapeDrawable -> background.paint.color =
                    ContextCompat.getColor(this, R.color.main_color)
                is GradientDrawable -> background.setColor(
                    ContextCompat.getColor(
                        this,
                        R.color.main_color
                    )
                )
                is ColorDrawable -> background.color =
                    ContextCompat.getColor(this, R.color.main_color)
            }
        }
    }

    private fun intent() {
        act_home_iv_go_route.setOnClickListener {
            var goToRoute = Intent(this, RouteActivity::class.java)
            startActivity(goToRoute)
        }
    }
}
