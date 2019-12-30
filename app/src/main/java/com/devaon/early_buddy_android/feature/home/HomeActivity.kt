package com.devaon.early_buddy_android.feature.home

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.feature.schedule.ScheduleActivity
import com.devaon.early_buddy_android.feature.util.TextViewIntAnimation
import com.devaon.early_buddy_android.feature.calendar.CalendarActivity
import com.devaon.early_buddy_android.feature.route.RouteActivity
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*
import kotlin.concurrent.timer

class HomeActivity : AppCompatActivity() {

    private var time = 60000
    private var timerTask: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        act_home_tv_minute_number.setAnimInt(time)
        start()
        changeColor()
        intent()
    }

    private fun changeColor() {
        act_home_tv_bus_number.setOnClickListener {
            when (val background = act_home_tv_bus_number.getBackground()) {
                is GradientDrawable -> background.setColor(ContextCompat.getColor(this, R.color.main_color))
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

    private fun start() { //타이머 스타트

        timerTask = timer(period = 10) {
            // period = 10 0.01초 , period = 1000 면 1초
            time--
            // 0.01초마다 변수를 증가시킴

            val hour = (time / 144000) % 24 // 1시간
            val min = (time / 6000) % 60 // 1분
            val sec = (time / 100) % 60 //1초
            val milli = time % 100 // 0.01 초
            runOnUiThread {
                // Ui 를 갱신 시킴.

                if (min < 10) { // 분
                    act_home_tv_minute_number.text = "0$min"
                } else {
                    act_home_tv_minute_number.text = "$min"
                }

//                if (sec < 10) { // 초
//                    act_home_tv_minute_number.text = "0$sec"
//                } else {
//                    act_home_tv_minute_number.text = "$sec"
//                }
////
//                if (milli < 10){
//                    act_home_tv_minute_number.text = "0$milli"
//                }else {
//                    act_home_tv_minute_number.text = "$milli"
//                }

                //$ 를 붙여주면 변하는 값을 계속 덮어준다
                //ex) $를 붙여주면 기존에 1이라는 값이 잇을때 값이 2로변하면 2로 바꿔준다.

            }
        }
    }

}
