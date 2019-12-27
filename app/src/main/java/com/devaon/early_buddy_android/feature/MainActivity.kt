package com.devaon.early_buddy_android.feature

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.devaon.early_buddy_android.R
import kotlinx.android.synthetic.main.activity_home.*
import net.daum.mf.map.api.MapView


class MainActivity : AppCompatActivity() {

    lateinit var mMapView:MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

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
//        mMapView.setCurrentLocationEventListener(this)
    }
}
