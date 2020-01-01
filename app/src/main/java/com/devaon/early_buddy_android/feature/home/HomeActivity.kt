package com.devaon.early_buddy_android.feature.home

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.marginBottom
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.schedule.HomeScheduleResponse
import com.devaon.early_buddy_android.feature.calendar.CalendarActivity
import com.devaon.early_buddy_android.feature.route.RouteActivity
import com.devaon.early_buddy_android.feature.schedule.ScheduleActivity
import com.devaon.early_buddy_android.feature.util.TextViewIntAnimation
import com.devaon.early_buddy_android.network.EarlyBuddyServiceImpl
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.concurrent.timer

class HomeActivity : AppCompatActivity() {

    private var time = 60000
    private var timerTask: Timer? = null
    var minmin: String = ""
    var secsec: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        start()
        changeColor()
        intent()
        homeNetwork()
    }

    private fun homeNetwork() {

        val callRoute: Call<HomeScheduleResponse> = EarlyBuddyServiceImpl.service.getHomeSchedule(8)

        callRoute.enqueue(object : Callback<HomeScheduleResponse> {
            override fun onFailure(call: Call<HomeScheduleResponse>, t: Throwable) {
                Log.e("error is ", t.message)
            }

            override fun onResponse(
                call: Call<HomeScheduleResponse>,
                response: Response<HomeScheduleResponse>
            ) {
                var homeScheduleResponse = response.body()!!

                val date1 = LocalDateTime.now()
                val nowFormat = DateTimeFormatter.ISO_DATE_TIME
                val formatted = date1.format(nowFormat)
                val scheduleTime =
                    homeScheduleResponse.homeSchedule.scheduleSummaryData.scheduleStartTime

                val date2 = LocalDateTime.of(
                    Integer.valueOf(scheduleTime.substring(0, 4)),      //년
                    Integer.valueOf(scheduleTime.substring(5, 7)),      //월
                    Integer.valueOf(scheduleTime.substring(8, 10)),      //일
                    Integer.valueOf(scheduleTime.substring(11, 13)),    //시간
                    Integer.valueOf(scheduleTime.substring(14, 16)),    //분
                    Integer.valueOf(scheduleTime.substring(17, 19))     //초
                )

                if (date2.dayOfMonth == 0) {  //0일 일때(시간을 표현)
                    when(homeScheduleResponse.homeSchedule.ready){
                        false -> {
                            act_home_tv_bus_number.visibility=View.GONE
                            act_home_tv_bus_current_location.visibility=View.GONE
                            act_home_tv_next_bus.visibility=View.GONE
                            act_home_tv_next_bus_var.visibility=View.GONE
                            act_homme_iv_reboot.visibility=View.GONE
                            act_home_tv_minute_number.setAnimInt(date2.minusHours(date1.hour.toLong()).hour)
                            act_home_tv_before_minute.text = "시간 전"
                            act_home_iv_text.setImageResource(R.drawable.text_daily)
                            act_home_iv_bottom_img.setImageResource(R.drawable.img_late_bg)
                            act_home_tv_first_promise.text = homeScheduleResponse.homeSchedule.scheduleSummaryData.scheduleName
                            act_home_tv_third_place.text = homeScheduleResponse.homeSchedule.scheduleSummaryData.endAddress
                        }
                        true -> {
                            act_home_tv_minute_number.setAnimInt(date2.minusHours(date1.hour.toLong()).hour)
                            act_home_tv_before_minute.text = "시간 전"
                            act_home_iv_text.setImageResource(R.drawable.text_daily)
                            act_home_iv_bottom_img.setImageResource(R.drawable.img_late_bg)
                            act_home_tv_first_promise.text = homeScheduleResponse.homeSchedule.scheduleSummaryData.scheduleName
                            act_home_tv_third_place.text = homeScheduleResponse.homeSchedule.scheduleSummaryData.endAddress
                        }
                    }
                    act_home_tv_minute_number.setAnimInt(59)
                    act_home_tv_before_minute.text = "시간 전"
                    act_home_tv_minute_number.text = date2.minusHours(date1.hour.toLong()).hour.toString()
                    act_home_iv_text.setImageResource(R.drawable.text_daily)
                    act_home_iv_bottom_img.setImageResource(R.drawable.img_late_bg)
                    act_home_tv_first_promise.text = homeScheduleResponse.homeSchedule.scheduleSummaryData.scheduleName
                    act_home_tv_third_place.text = homeScheduleResponse.homeSchedule.scheduleSummaryData.endAddress
                }
                else if (date2.minusDays(date1.dayOfMonth.toLong()).dayOfMonth <= 7) {      //7일 이하 일 때
                    act_home_tv_bus_number.visibility=View.GONE
                    act_home_tv_bus_current_location.visibility=View.GONE
                    act_home_tv_next_bus.visibility=View.GONE
                    act_home_tv_next_bus_var.visibility=View.GONE
                    act_homme_iv_reboot.visibility=View.GONE
                    act_home_tv_before_minute.text = "일 전"
                    act_home_tv_minute_number.text = date2.minusDays(date1.dayOfMonth.toLong()).dayOfMonth.toString()
                    act_home_iv_text.setImageResource(R.drawable.text_daily)
                    act_home_iv_bottom_img.setImageResource(R.drawable.img_bg_relax)
                    act_home_tv_first_promise.text = homeScheduleResponse.homeSchedule.scheduleSummaryData.scheduleName
                    act_home_tv_third_place.text = homeScheduleResponse.homeSchedule.scheduleSummaryData.endAddress
                }
                Log.e("date1 is", date2.dayOfMonth.toString())
                Log.e("date2 is", date1.dayOfMonth.toString())
                Log.e("now is", date2.minusDays(date1.dayOfMonth.toLong()).dayOfMonth.toString())
            }
        }
        )
    }

    private fun changeColor() {
        act_home_tv_bus_number.setOnClickListener {
            when (val background = act_home_tv_bus_number.getBackground()) {
                is GradientDrawable -> background.setColor(
                    ContextCompat.getColor(
                        this,
                        R.color.main_color
                    )
                )
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

    private fun TextView.setAnimInt(value: Int) {
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
                    minmin = "0$min"
                } else {
                    minmin = "$min"
                }

                if (sec < 10) { // 초
                    secsec = "0$sec"
                } else {
                    secsec = "$sec"
                }

                act_home_tv_next_bus_var.text = String.format("%s분 %s초전", minmin, secsec)
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
