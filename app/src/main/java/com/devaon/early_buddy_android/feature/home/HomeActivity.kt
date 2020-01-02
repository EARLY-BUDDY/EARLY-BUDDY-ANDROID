package com.devaon.early_buddy_android.feature.home

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.schedule.HomeScheduleResponse
import com.devaon.early_buddy_android.feature.calendar.CalendarActivity
import com.devaon.early_buddy_android.feature.route.RouteActivity
import com.devaon.early_buddy_android.feature.schedule.ScheduleActivity
import com.devaon.early_buddy_android.network.EarlyBuddyServiceImpl
import com.devaon.early_buddy_android.util.TextViewIntAnimation
import com.devaon.early_buddy_android.util.view.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.util.*
import kotlin.concurrent.timer


class HomeActivity : AppCompatActivity() {

    var time = 0
    private var timerTask: Timer? = null
    var minmin: String = ""
    var token: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        reboot()
        intent()
        homeNetwork()
    }

    private fun homeNetwork() {

        val callRoute: Call<HomeScheduleResponse> = EarlyBuddyServiceImpl.service.getHomeSchedule(7)

        callRoute.enqueue(object : Callback<HomeScheduleResponse> {
            override fun onFailure(call: Call<HomeScheduleResponse>, t: Throwable) {
                Log.e("error is ", t.message)
            }

            override fun onResponse(
                call: Call<HomeScheduleResponse>,
                response: Response<HomeScheduleResponse>
            ) {
                var homeScheduleResponse = response.body()!!

                val nowDate = LocalDateTime.now()
                val scheduleStartTime =
                    homeScheduleResponse.homeSchedule.scheduleSummaryData.scheduleStartTime

                val promiseTime = LocalDateTime.of(
                    Integer.valueOf(scheduleStartTime.substring(0, 4)),      //년
                    Integer.valueOf(scheduleStartTime.substring(5, 7)),      //월
                    Integer.valueOf(scheduleStartTime.substring(8, 10)),      //일
                    Integer.valueOf(scheduleStartTime.substring(11, 13)),    //시간
                    Integer.valueOf(scheduleStartTime.substring(14, 16)),    //분
                    Integer.valueOf(scheduleStartTime.substring(17, 19))     //초
                )


                if (promiseTime.dayOfMonth - nowDate.dayOfMonth == 0) {      //0일 일때(시간을 표현)
                    when (homeScheduleResponse.homeSchedule.ready) {
                        false -> {
                            act_home_tv_arrive_text.text = "오늘 일정까지"
                            var bottomParams =
                                act_home_cl_middle_bar.layoutParams  as? ConstraintLayout.LayoutParams
                            bottomParams?.topMargin = 90
                            act_home_cl_middle_bar.layoutParams = bottomParams

                            viewGone()

                            if (promiseTime.minusHours(nowDate.hour.toLong()).hour == 0) {
                                act_home_tv_minute_number.setAnimInt(
                                    promiseTime.minusMinutes(
                                        nowDate.minute.toLong()
                                    ).minute
                                )
                                act_home_tv_before_minute.text = "분 전"
                            } else {
                                act_home_tv_minute_number.setAnimInt(promiseTime.minusHours(nowDate.hour.toLong()).hour)
                                act_home_tv_before_minute.text = "시간 전"
                            }

                            if (homeScheduleResponse.homeSchedule.isGoing == 1) {     //가는 중일 때
                                act_home_tv_move.visibility = View.VISIBLE
                                var goingHour = 0
                                var goingMin = 0
                                if(promiseTime.minute-nowDate.minute < 0){
                                    goingMin = 60 + (promiseTime.minute-nowDate.minute)
                                    when {
                                        promiseTime.hour-nowDate.hour>=1 -> goingHour = promiseTime.hour-nowDate.hour - 1
                                        else -> goingHour = 0
                                    }
                                }else{
                                    goingMin = promiseTime.minute-nowDate.minute
                                    goingHour = promiseTime.hour-nowDate.hour
                                }
                                act_home_tv_move.text = String.format("%d시간 %d분",goingHour,goingMin)
                                act_home_tv_arrive_text.text = "약속 시간까지"
                                act_home_tv_before_minute.visibility = View.INVISIBLE
                                act_home_tv_minute_number.visibility = View.INVISIBLE
                                act_home_iv_text.setImageResource(R.drawable.text_move)
                                act_home_iv_bottom_img.setImageResource(R.drawable.img_going)
                            } else {       //가는 중이 아닐 때
                                act_home_iv_text.setImageResource(R.drawable.text_daily)
                                act_home_iv_bottom_img.setImageResource(R.drawable.img_late_bg)
                            }
                            act_home_tv_first_promise.text =
                                homeScheduleResponse.homeSchedule.scheduleSummaryData.scheduleName
                            act_home_tv_third_place.text =
                                homeScheduleResponse.homeSchedule.scheduleSummaryData.endAddress
                        }
                        true -> {
                            val nextTransStartTime =
                                homeScheduleResponse.homeSchedule.nextTransArriveTime

                            if(nextTransStartTime != null){

                                val nextTransTime = LocalDateTime.of(
                                    Integer.valueOf(nextTransStartTime.substring(0, 4)),      //년
                                    Integer.valueOf(nextTransStartTime.substring(5, 7)),      //월
                                    Integer.valueOf(nextTransStartTime.substring(8, 10)),      //일
                                    Integer.valueOf(nextTransStartTime.substring(11, 13)),    //시간
                                    Integer.valueOf(nextTransStartTime.substring(14, 16)),    //분
                                    Integer.valueOf(nextTransStartTime.substring(17, 19))     //초
                                )

                                if (nextTransTime.minusHours(nowDate.hour.toLong()).hour >= 1) {
                                    if (nextTransTime.minute - nowDate.minute < 0) {
                                        act_home_tv_next_bus_var.text = String.format(
                                            "%d분전",
                                            60 + (nextTransTime.minute - nowDate.minute)
                                        )
                                    } else {
                                        act_home_tv_next_bus_var.text = String.format(
                                            "%d시간전",
                                            nextTransTime.minusHours(nowDate.hour.toLong()).hour
                                        )
                                    }

                                } else {
                                    act_home_tv_next_bus_var.text = String.format(
                                        "%d분전",
                                        nextTransTime.minusMinutes(nowDate.minute.toLong()).minute
                                    )
                                }

                            }


                            when (homeScheduleResponse.homeSchedule.firstTrans.trafficType) {
                                1 -> {      //지하철
                                    when (homeScheduleResponse.homeSchedule.lastTransCount) {
                                        1 -> {
                                            act_home_iv_bottom_img.setImageResource(com.devaon.early_buddy_android.R.drawable.img_late_bg)
                                            act_home_iv_text.setImageResource(com.devaon.early_buddy_android.R.drawable.img_main_text)
                                        }
                                        2 -> {
                                            act_home_iv_bottom_img.setImageResource(com.devaon.early_buddy_android.R.drawable.img_bg_onebus)
                                            act_home_iv_text.setImageResource(com.devaon.early_buddy_android.R.drawable.text_subway_one)
                                        }
                                        3 -> {
                                            act_home_iv_bottom_img.setImageResource(com.devaon.early_buddy_android.R.drawable.img_bg_twobus)
                                            act_home_iv_text.setImageResource(com.devaon.early_buddy_android.R.drawable.text_subway_two)
                                        }
                                        4 -> {
                                            act_home_iv_bottom_img.setImageResource(com.devaon.early_buddy_android.R.drawable.img_bg_threebus)
                                            act_home_iv_text.setImageResource(com.devaon.early_buddy_android.R.drawable.text_subway_three)
                                        }
                                    }
                                    when (homeScheduleResponse.homeSchedule.firstTrans.trafficType) {
                                        1 -> {
                                            when (val background =
                                                act_home_tv_bus_number.getBackground()) {
                                                is GradientDrawable -> background.setColor(
                                                    ContextCompat.getColor(
                                                        this@HomeActivity,
                                                        com.devaon.early_buddy_android.R.color.seoul_line_one
                                                    )
                                                )
                                            }
                                        }
                                        2 -> {
                                            when (val background =
                                                act_home_tv_bus_number.getBackground()) {
                                                is GradientDrawable -> background.setColor(
                                                    ContextCompat.getColor(
                                                        this@HomeActivity,
                                                        com.devaon.early_buddy_android.R.color.seoul_line_two
                                                    )
                                                )
                                            }
                                        }
                                        3 -> {
                                            when (val background =
                                                act_home_tv_bus_number.getBackground()) {
                                                is GradientDrawable -> background.setColor(
                                                    ContextCompat.getColor(
                                                        this@HomeActivity,
                                                        com.devaon.early_buddy_android.R.color.seoul_line_three
                                                    )
                                                )
                                            }
                                        }
                                        4 -> {
                                            when (val background =
                                                act_home_tv_bus_number.getBackground()) {
                                                is GradientDrawable -> background.setColor(
                                                    ContextCompat.getColor(
                                                        this@HomeActivity,
                                                        com.devaon.early_buddy_android.R.color.seoul_line_four
                                                    )
                                                )
                                            }
                                        }
                                        5 -> {
                                            when (val background =
                                                act_home_tv_bus_number.getBackground()) {
                                                is GradientDrawable -> background.setColor(
                                                    ContextCompat.getColor(
                                                        this@HomeActivity,
                                                        com.devaon.early_buddy_android.R.color.seoul_line_five
                                                    )
                                                )
                                            }
                                        }
                                        6 -> {
                                            when (val background =
                                                act_home_tv_bus_number.getBackground()) {
                                                is GradientDrawable -> background.setColor(
                                                    ContextCompat.getColor(
                                                        this@HomeActivity,
                                                        com.devaon.early_buddy_android.R.color.seoul_line_six
                                                    )
                                                )
                                            }
                                        }
                                        7 -> {
                                            when (val background =
                                                act_home_tv_bus_number.getBackground()) {
                                                is GradientDrawable -> background.setColor(
                                                    ContextCompat.getColor(
                                                        this@HomeActivity,
                                                        com.devaon.early_buddy_android.R.color.seoul_line_seven
                                                    )
                                                )
                                            }
                                        }
                                        8 -> {
                                            when (val background =
                                                act_home_tv_bus_number.getBackground()) {
                                                is GradientDrawable -> background.setColor(
                                                    ContextCompat.getColor(
                                                        this@HomeActivity,
                                                        com.devaon.early_buddy_android.R.color.seoul_line_eight
                                                    )
                                                )
                                            }
                                        }
                                        9 -> {
                                            when (val background =
                                                act_home_tv_bus_number.getBackground()) {
                                                is GradientDrawable -> background.setColor(
                                                    ContextCompat.getColor(
                                                        this@HomeActivity,
                                                        com.devaon.early_buddy_android.R.color.seoul_line_nine
                                                    )
                                                )
                                            }
                                        }
                                        100 -> {
                                            when (val background =
                                                act_home_tv_bus_number.getBackground()) {
                                                is GradientDrawable -> background.setColor(
                                                    ContextCompat.getColor(
                                                        this@HomeActivity,
                                                        com.devaon.early_buddy_android.R.color.seoul_line_bunDang
                                                    )
                                                )
                                            }
                                        }
                                        101 -> {
                                            when (val background =
                                                act_home_tv_bus_number.getBackground()) {
                                                is GradientDrawable -> background.setColor(
                                                    ContextCompat.getColor(
                                                        this@HomeActivity,
                                                        com.devaon.early_buddy_android.R.color.seoul_line_gongHang
                                                    )
                                                )
                                            }
                                        }
                                        104 -> {
                                            when (val background =
                                                act_home_tv_bus_number.getBackground()) {
                                                is GradientDrawable -> background.setColor(
                                                    ContextCompat.getColor(
                                                        this@HomeActivity,
                                                        com.devaon.early_buddy_android.R.color.seoul_line_gyungJung
                                                    )
                                                )
                                            }
                                        }
                                        107 -> {
                                            when (val background =
                                                act_home_tv_bus_number.getBackground()) {
                                                is GradientDrawable -> background.setColor(
                                                    ContextCompat.getColor(
                                                        this@HomeActivity,
                                                        com.devaon.early_buddy_android.R.color.seoul_line_ever
                                                    )
                                                )
                                            }
                                        }
                                        108 -> {
                                            when (val background =
                                                act_home_tv_bus_number.getBackground()) {
                                                is GradientDrawable -> background.setColor(
                                                    ContextCompat.getColor(
                                                        this@HomeActivity,
                                                        com.devaon.early_buddy_android.R.color.seoul_line_gyungChun
                                                    )
                                                )
                                            }
                                        }
                                        102 -> {
                                            when (val background =
                                                act_home_tv_bus_number.getBackground()) {
                                                is GradientDrawable -> background.setColor(
                                                    ContextCompat.getColor(
                                                        this@HomeActivity,
                                                        com.devaon.early_buddy_android.R.color.seoul_line_jaGiBuSang
                                                    )
                                                )
                                            }
                                        }
                                        109 -> {
                                            when (val background =
                                                act_home_tv_bus_number.getBackground()) {
                                                is GradientDrawable -> background.setColor(
                                                    ContextCompat.getColor(
                                                        this@HomeActivity,
                                                        com.devaon.early_buddy_android.R.color.seoul_line_sinBunDang
                                                    )
                                                )
                                            }
                                        }
                                        110 -> {
                                            when (val background =
                                                act_home_tv_bus_number.getBackground()) {
                                                is GradientDrawable -> background.setColor(
                                                    ContextCompat.getColor(
                                                        this@HomeActivity,
                                                        com.devaon.early_buddy_android.R.color.seoul_line_uiJeongBu
                                                    )
                                                )
                                            }
                                        }
                                        113 -> {
                                            when (val background =
                                                act_home_tv_bus_number.getBackground()) {
                                                is GradientDrawable -> background.setColor(
                                                    ContextCompat.getColor(
                                                        this@HomeActivity,
                                                        com.devaon.early_buddy_android.R.color.seoul_line_ueeSinSeol
                                                    )
                                                )
                                            }
                                        }
                                    }
                                    act_home_tv_bus_current_location.text =
                                        homeScheduleResponse.homeSchedule.firstTrans.detailStartAddress
                                    act_home_tv_bus_number.text = String.format(
                                        "%d호선",
                                        homeScheduleResponse.homeSchedule.firstTrans.subwayLane
                                    )
                                    act_home_tv_arrive_text.text = "열차 도착까지"
                                }
                                2 -> {        //버스
                                    when (homeScheduleResponse.homeSchedule.lastTransCount) {
                                        1 -> {
                                            act_home_iv_bottom_img.setImageResource(com.devaon.early_buddy_android.R.drawable.img_late_bg)
                                            act_home_iv_text.setImageResource(com.devaon.early_buddy_android.R.drawable.img_main_text)
                                        }
                                        2 -> {
                                            act_home_iv_bottom_img.setImageResource(com.devaon.early_buddy_android.R.drawable.img_bg_onebus)
                                            act_home_iv_text.setImageResource(com.devaon.early_buddy_android.R.drawable.text_one)
                                        }
                                        3 -> {
                                            act_home_iv_bottom_img.setImageResource(com.devaon.early_buddy_android.R.drawable.img_bg_twobus)
                                            act_home_iv_text.setImageResource(com.devaon.early_buddy_android.R.drawable.text_two)
                                        }
                                        3 -> {
                                            act_home_iv_bottom_img.setImageResource(com.devaon.early_buddy_android.R.drawable.img_bg_threebus)
                                            act_home_iv_text.setImageResource(com.devaon.early_buddy_android.R.drawable.text_three)
                                        }
                                    }
                                    when (homeScheduleResponse.homeSchedule.firstTrans.busType) {
                                        1, 2, 11 -> {
                                            when (val background =
                                                act_home_tv_bus_number.getBackground()) {
                                                is GradientDrawable -> background.setColor(
                                                    ContextCompat.getColor(
                                                        this@HomeActivity,
                                                        com.devaon.early_buddy_android.R.color.seoul_bus_gan_line
                                                    )
                                                )
                                            }
                                        }
                                        10, 12 -> {
                                            when (val background =
                                                act_home_tv_bus_number.getBackground()) {
                                                is GradientDrawable -> background.setColor(
                                                    ContextCompat.getColor(
                                                        this@HomeActivity,
                                                        com.devaon.early_buddy_android.R.color.seoul_bus_ji_line
                                                    )
                                                )
                                            }
                                        }
                                        4, 14, 15 -> {
                                            when (val background =
                                                act_home_tv_bus_number.getBackground()) {
                                                is GradientDrawable -> background.setColor(
                                                    ContextCompat.getColor(
                                                        this@HomeActivity,
                                                        com.devaon.early_buddy_android.R.color.seoul_bus_gwangyuk
                                                    )
                                                )
                                            }
                                        }
                                        5 -> {
                                            when (val background =
                                                act_home_tv_bus_number.getBackground()) {
                                                is GradientDrawable -> background.setColor(
                                                    ContextCompat.getColor(
                                                        this@HomeActivity,
                                                        com.devaon.early_buddy_android.R.color.seoul_line_gongHang
                                                    )
                                                )
                                            }
                                        }
                                        13 -> {
                                            when (val background =
                                                act_home_tv_bus_number.getBackground()) {
                                                is GradientDrawable -> background.setColor(
                                                    ContextCompat.getColor(
                                                        this@HomeActivity,
                                                        com.devaon.early_buddy_android.R.color.inCheon_line_two
                                                    )
                                                )
                                            }
                                        }
                                    }
                                    act_home_tv_bus_current_location.text =
                                        homeScheduleResponse.homeSchedule.firstTrans.detailStartAddress
                                    act_home_tv_bus_number.text =
                                        homeScheduleResponse.homeSchedule.firstTrans.busNo.toString()
                                    act_home_tv_arrive_text.text = "버스 도착까지"

                                }
                            }
                            val firstTransTime =
                                homeScheduleResponse.homeSchedule.arriveTime
                            val firstArriveTime = LocalDateTime.of(
                                Integer.valueOf(firstTransTime.substring(0, 4)),      //년
                                Integer.valueOf(firstTransTime.substring(5, 7)),      //월
                                Integer.valueOf(firstTransTime.substring(8, 10)),      //일
                                Integer.valueOf(firstTransTime.substring(11, 13)),    //시간
                                Integer.valueOf(firstTransTime.substring(14, 16)),    //분
                                Integer.valueOf(firstTransTime.substring(17, 19))     //초
                            )
                            time =
                                firstArriveTime.minusMinutes(nowDate.minute.toLong()).minute * 6000
                            if(firstArriveTime.minusHours(nowDate.hour.toLong()).hour <= 1){
                                if(firstArriveTime.minusHours(nowDate.hour.toLong()).hour==1){
                                    if (firstArriveTime.minute - nowDate.minute < 0) {
                                        if (firstArriveTime.minusMinutes(nowDate.minute.toLong()).minute > 3) {
                                            act_home_tv_minute_number.setAnimInt(60 + (firstArriveTime.minute - nowDate.minute))
                                        }
                                        act_home_tv_minute_number.start(token++)
                                        act_home_tv_before_minute.text = "분 전"
                                    }
                                    else{
                                        act_home_tv_minute_number.setAnimInt(1)
                                        act_home_tv_before_minute.text = "시간 전"
                                    }
                                }
                                else{
                                    act_home_tv_minute_number.setAnimInt(firstArriveTime.minusMinutes(nowDate.minute.toLong()).minute)
                                    act_home_tv_before_minute.text = "분 전"
                                }
                            }else{
                                act_home_tv_minute_number.setAnimInt(firstArriveTime.minusHours(nowDate.hour.toLong()).hour)
                                act_home_tv_before_minute.text = "시간 전"
                            }
                            act_home_tv_first_promise.text =
                                homeScheduleResponse.homeSchedule.scheduleSummaryData.scheduleName
                            act_home_tv_third_place.text =
                                homeScheduleResponse.homeSchedule.scheduleSummaryData.endAddress
                        }
                    }
                } else if (promiseTime.minusDays(nowDate.dayOfMonth.toLong()).dayOfMonth <= 7) {      //7일 이하 일 때
                    var bottomParams =
                        act_home_cl_middle_bar.layoutParams  as? ConstraintLayout.LayoutParams
                    bottomParams?.topMargin = 90
                    act_home_cl_middle_bar.layoutParams = bottomParams
                    act_home_tv_arrive_text.text = "다음 일정까지"
                    viewGone()
                    act_home_tv_before_minute.text = "일 전"
                    act_home_tv_minute_number.text =
                        promiseTime.minusDays(nowDate.dayOfMonth.toLong()).dayOfMonth.toString()
                    act_home_iv_text.setImageResource(R.drawable.text_daily)
                    act_home_iv_bottom_img.setImageResource(R.drawable.img_bg_relax)
                    act_home_tv_first_promise.text =
                        homeScheduleResponse.homeSchedule.scheduleSummaryData.scheduleName
                    act_home_tv_third_place.text =
                        homeScheduleResponse.homeSchedule.scheduleSummaryData.endAddress
                }
                var amPm = ""
                var promiseHour = ""
                var promiseMinute = ""
                when {
                    Integer.valueOf(scheduleStartTime.substring(11, 13)) >= 12 -> {
                        amPm = "오후"
                        promiseHour = scheduleStartTime.substring(11, 13)
                        promiseMinute = scheduleStartTime.substring(14, 16)
                    }
                    else -> {
                        amPm = "오전"
                        promiseHour = scheduleStartTime.substring(11, 13)
                        promiseMinute = scheduleStartTime.substring(14, 16)
                    }
                }
                act_home_tv_second_time.text =
                    String.format("%s %s : %s", amPm, promiseHour, promiseMinute)
            }
        }
        )
    }

    private fun reboot() {
        act_homme_iv_reboot.setOnClickListener {
            homeNetwork()
            val anim = AnimationUtils.loadAnimation(
                applicationContext, // 현재 화면의 제어권자
                R.anim.act_home_animate_rotate
            )    // 설정한 에니메이션 파일
            act_homme_iv_reboot.startAnimation(anim)
        }
        act_home_tv_next_bus_var.setOnClickListener {
            homeNetwork()
            val anim = AnimationUtils.loadAnimation(
                applicationContext, // 현재 화면의 제어권자
                R.anim.act_home_animate_rotate
            )    // 설정한 에니메이션 파일
            act_homme_iv_reboot.startAnimation(anim)
        }
    }

    private fun intent() {
        act_home_iv_go_route.setSafeOnClickListener {
            var goToRoute = Intent(this, RouteActivity::class.java)
            startActivity(goToRoute)
        }

        act_home_iv_planner.setSafeOnClickListener {
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

    private fun TextView.start(token: Int) { //타이머 스타트
        var a = this
        if (token == 0) {
            timerTask = timer(period = 10) {
                // period = 10 0.01초 , period = 1000 면 1초
                time--

                val min = (time / 6000) % 60 // 1분
                runOnUiThread {
                    // Ui 를 갱신 시킴.

                    if (min < 10) { // 분
                        minmin = "$min"
                    } else {
                        minmin = "$min"
                    }
//                    act_home_tv_minute_number.text = String.format("%s", minmin)

                    a.text = String.format("%s", minmin)
                    if (Integer.valueOf(minmin) <= 3) {
                        act_home_tv_minute_number.visibility = View.INVISIBLE
                        act_home_tv_before_minute.visibility = View.INVISIBLE
                        act_home_tv_soon.visibility = View.VISIBLE
                    }


                }
            }
        }
    }

    private fun viewGone() {
        act_home_tv_bus_number.visibility = View.GONE
        act_home_tv_bus_current_location.visibility = View.GONE
        act_home_tv_next_bus.visibility = View.GONE
        act_home_tv_next_bus_var.visibility = View.GONE
        act_homme_iv_reboot.visibility = View.GONE
    }
}
