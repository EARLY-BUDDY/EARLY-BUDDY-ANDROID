package com.devaon.early_buddy_android.feature

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.util.TextViewIntAnimation
import com.devaon.early_buddy_android.feature.calendar.CalendarActivity
import com.devaon.early_buddy_android.feature.route.RouteActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    var a = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        act_home_tv_minute_number.setAnimInt(60)
        changeColor()
        intent()
    }

    private fun changeColor() {
        act_home_tv_bus_number.setOnClickListener {
            when (val background = act_home_tv_bus_number.getBackground()) {
                is GradientDrawable ->
                    if(a==1){
                    background.setColor(ContextCompat.getColor(this, R.color.main_color))
                    }else{
                        background.setColor(ContextCompat.getColor(this, R.color.seoul_line_seven))
                    }

//                is ShapeDrawable -> background.paint.color =
//                    ContextCompat.getColor(this, R.color.main_color)
//                is ColorDrawable -> background.color =
//                    ContextCompat.getColor(this, R.color.main_color)
            }
        }
    }

    private fun intent() {
        act_home_iv_go_route.setOnClickListener {
            var goToRoute = Intent(this, RouteActivity::class.java)
            startActivity(goToRoute)
        }

        act_home_iv_planner.setOnClickListener {
            var goToPlanner = Intent(this, CalendarActivity::class.java)
            startActivity(goToPlanner)
        }

        act_home_iv_plus.setOnClickListener {
            var goToAddSchedule = Intent(this, ScheduleActivity::class.java)
            startActivity(goToAddSchedule)
        }

    }
    fun TextView.setAnimInt(value: Int) {
        startAnimation(TextViewIntAnimation(this, to = value))
    }
}
