package com.devaon.early_buddy_android.feature.schedule

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.schedule.GetScheduleData
import com.devaon.early_buddy_android.data.schedule.HomeScheduleResponse
import com.devaon.early_buddy_android.data.schedule.PostScheduleData
import com.devaon.early_buddy_android.network.EarlyBuddyServiceImpl
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.view.View
import android.view.View.VISIBLE
import android.widget.*
import com.devaon.early_buddy_android.feature.calendar.CalendarActivity.getCalendarAcitivityObject.position
import kotlinx.android.synthetic.main.activity_schedule_complete.*


class ScheduleCompleteActivity: AppCompatActivity(){

    lateinit var scheName: TextView
    lateinit var scheDay: TextView
    lateinit var scheTime: TextView
    lateinit var scheFrom: TextView
    lateinit var scheTo: TextView
    lateinit var scheNoti: TextView
    lateinit var scheNotiRange: TextView

    lateinit var mon: ImageView
    lateinit var tue: ImageView
    lateinit var wed: ImageView
    lateinit var thu: ImageView
    lateinit var fri: ImageView
    lateinit var sat: ImageView
    lateinit var sun: ImageView


    var scheduleIdx = 0
    lateinit var routeTime: TextView
    lateinit var routeMethod: TextView
    lateinit var walk1 : RelativeLayout
    lateinit var walk2 : RelativeLayout
    lateinit var walk3 : RelativeLayout
    lateinit var walk4 : RelativeLayout

    lateinit var method1: RelativeLayout
    lateinit var method2: RelativeLayout
    lateinit var method3: RelativeLayout

    lateinit var method1Img: ImageView
    lateinit var method2Img: ImageView
    lateinit var method3Img: ImageView

    lateinit var method1Tx: TextView
    lateinit var method2Tx: TextView
    lateinit var method3Tx: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_complete)

        setButton()

        scheName = findViewById(R.id.act_schedule_complete_tv_sche_name)
        scheDay = findViewById(R.id.act_schedule_complete_tv_sche_day)
        scheTime = findViewById(R.id.act_schedule_complete_tv_sche_time)
        scheFrom = findViewById(R.id.act_schedule_complete_complete_tv_from)
        scheTo = findViewById(R.id.act_schedule_complete_complete_tv_to)
        scheNoti = findViewById(R.id.act_schedule_complete_complete_tv_noti_pick)
        scheNotiRange = findViewById(R.id.act_schedule_complete_tv_noti_range_pick)
        routeTime = findViewById(R.id.act_schedule_route_time)
        routeMethod = findViewById(R.id.act_schedule_route_tv_method)

        mon = findViewById(R.id.act_schedule_complete_iv_mon)
        tue = findViewById(R.id.act_schedule_complete_iv_tue)
        wed = findViewById(R.id.act_schedule_complete_iv_thu)
        thu = findViewById(R.id.act_schedule_complete_iv_thu)
        fri = findViewById(R.id.act_schedule_complete_iv_fri)
        sat = findViewById(R.id.act_schedule_complete_iv_sat)
        sun = findViewById(R.id.act_schedule_complete_iv_sun)

        scheduleIdx = intent.getIntExtra("scheduleIdx", 0)
        walk1 = findViewById(R.id.act_schedule_route_rl_walk_1)
        walk2 = findViewById(R.id.act_schedule_route_rl_walk_2)
        walk3 = findViewById(R.id.act_schedule_route_rl_walk_3)
        walk4 = findViewById(R.id.act_schedule_route_rl_walk_4)

        method1 = findViewById(R.id.act_schedule__route_rl_method_1)
        method2 = findViewById(R.id.act_schedule_route_rl_method_2)
        method3 = findViewById(R.id.act_schedule_route_rl_method_3)

        method1Img = findViewById(R.id.act_schedule_route_iv_method_1)
        method2Img = findViewById(R.id.act_schedule_route_iv_method_2)
        method3Img = findViewById(R.id.act_schedule_route_iv_method_3)

        method1Tx = findViewById(R.id.act_schedule_route_tv_method_1)
        method2Tx = findViewById(R.id.act_schedule_route_tv_method_2)
        method3Tx = findViewById(R.id.act_schedule_route_tv_method_3)

        val scheduleIdx = intent.getIntExtra("scheduleIdx", 0)
        Log.e("schedule", scheduleIdx.toString())

        getSchedule()
    }

    private fun getSchedule(){

        val callGetSchedule: Call<GetScheduleData> = EarlyBuddyServiceImpl.service.getSchedule(scheduleIdx)

        callGetSchedule.enqueue(object : Callback<GetScheduleData>{
            override fun onFailure(call: Call<GetScheduleData>, t: Throwable) {
                Log.e("getSchedule error is ", t.message)
            }

            override fun onResponse(
                call: Call<GetScheduleData>,
                response: Response<GetScheduleData>
            ) {
                var getScheduleResponse = response.body()!!
                Log.e("스케쥴 상세정보 내놔아아", getScheduleResponse.toString())

                //일정 제목 표시
                scheName.text = getScheduleResponse.data.scheduleInfo.scheduleName
                //일정 시간 표시
                scheDay.text = getScheduleResponse.data.scheduleInfo.scheduleStartDay.replace("-",".")
                var amPm = ""
                var hours = 0
                var min = ""
                when {
                    Integer.valueOf(getScheduleResponse.data.scheduleInfo.scheduleStartTime.substring(0,2)) > 12 -> {
                        amPm ="오후"
                        hours = Integer.valueOf(getScheduleResponse.data.scheduleInfo.scheduleStartTime.substring(0,2)) - 12
                    }
                    Integer.valueOf(getScheduleResponse.data.scheduleInfo.scheduleStartTime.substring(0,2)) == 12 -> {
                        amPm = "오후"
                        hours = 12
                    }
                    else -> {
                        amPm ="오전"
                        hours = Integer.valueOf(getScheduleResponse.data.scheduleInfo.scheduleStartTime.substring(0,2))
                    }
                }
                min = getScheduleResponse.data.scheduleInfo.scheduleStartTime.substring(3,5)
                scheTime.text = String.format("%s %d:%s", amPm, hours, min)

                //일정 장소 표시
                scheFrom.text = getScheduleResponse.data.scheduleInfo.startAddress
                scheTo.text = getScheduleResponse.data.scheduleInfo.endAddress

                //경로 시간 표시
                val routeHour = getScheduleResponse.data.pathInfo.totalTime / 60
                val routeMin = getScheduleResponse.data.pathInfo.totalTime % 60
                if(routeHour != 0){
                    if(routeMin != 0){
                        routeTime.text = String.format("%d시간 %d분", routeHour, routeMin)
                    }else{
                        routeTime.text = String.format("%d시간", routeHour)
                    }
                } else {
                    if(routeMin != 0){
                        routeTime.text = String.format("%d분", routeMin)
                    }
                }

                //경로 메소드 표시
                when(getScheduleResponse.data.pathInfo.pathType){
                    1 -> { routeMethod.text = "지하철" }
                    2 -> { routeMethod.text = "버스" }
                    3 -> { routeMethod.text = "지하철 + 버스"}
                }

                //경로 바 표시
                var transText = arrayListOf<String>()
                var transColor = arrayListOf<String>()

                for(i in 1.. getScheduleResponse.data.pathInfo.subPath.size-1) {
                    if(i % 2 != 0) { // 홀수만 처리
                        when (getScheduleResponse.data.pathInfo.subPath[i].trafficType) {
                            1 -> { // 지하철
                                when(getScheduleResponse.data.pathInfo.subPath[i].subwayLane){
                                    1-> {
                                        transText.add("1호선")
                                        transColor.add("#243899")
                                    }
                                    2->{
                                        transText.add("2호선")
                                        transColor.add("#35b645")
                                    }
                                    3->{
                                        transText.add("3호선")
                                        transColor.add("#f36e00")
                                    }
                                    4->{
                                        transText.add("4호선")
                                        transColor.add("#219de2")
                                    }
                                    5->{
                                        transText.add("5호선")
                                        transColor.add("#8828e2")
                                    }
                                    6->{
                                        transText.add("6호선")
                                        transColor.add("#b75000")
                                    }
                                    7->{
                                        transText.add("7호선")
                                        transColor.add("#697305")
                                    }
                                    8->{
                                        transText.add("8호선")
                                        transColor.add("#e8146d")
                                    }
                                    9->{
                                        transText.add("9호선")
                                        transColor.add("#d2a715")
                                    }

                                    100 -> {
                                        transText.add("분당선")
                                        transColor.add("#eeaa00")
                                    }
                                    101 -> {
                                        transText.add("공항철도")
                                        transColor.add("#70b5e6")
                                    }
                                    104 -> {
                                        transText.add("경의중앙선")
                                        transColor.add("#7ac6a4")
                                    }
                                    107 -> {
                                        transText.add("에버라인")
                                        transColor.add("#75c56e")
                                    }
                                    108 -> {
                                        transText.add("경춘선")
                                        transColor.add("#00b07a")
                                    }
                                    102 -> {
                                        transText.add("자기부상철도")
                                        transColor.add("#f08d41")
                                    }
                                    109 -> {
                                        transText.add("신분당선")
                                        transColor.add("#a71b2c")
                                    }
                                    110 -> {
                                        transText.add("의정부경전철")
                                        transColor.add("#ff9f00")
                                    }
                                    111 -> {
                                        transText.add("수인선")
                                        transColor.add("#eeaa00")
                                    }
                                    112 -> {
                                        transText.add("경강선")
                                        transColor.add("#1e6ff7")
                                    }
                                    113 -> {
                                        transText.add("우이신설선")
                                        transColor.add("#c7c300")
                                    }
                                    114 -> {
                                        transText.add("서해선")
                                        transColor.add("#8ac832")
                                    }
                                }
                            }
                            2 -> { // 일반
                                transText.add(getScheduleResponse.data.pathInfo.subPath[i].busNo.toString())
                                when(getScheduleResponse.data.pathInfo.subPath[i].busType){
                                    1, 2, 11 -> transColor.add("#3469ec")
                                    10,12 -> transColor.add("#33c63c")
                                    4, 14, 15 -> transColor.add("#ff574c")
                                    5 -> transColor.add("#70b5e5")
                                    else -> transColor.add("#85c900")
                                }
                            }
                        }
                    }

                }

                val walkParam1 = walk1.getLayoutParams() as LinearLayout.LayoutParams
                walkParam1.weight = getScheduleResponse.data.pathInfo.subPath[0].sectionTime.toFloat()
                walk1.setLayoutParams(walkParam1)

                val methodParam1 = method1.getLayoutParams() as LinearLayout.LayoutParams
                walkParam1.weight = getScheduleResponse.data.pathInfo.subPath[1].sectionTime.toFloat()
                method1.setLayoutParams(methodParam1)
                method1Tx.text = transText[0]
                method1Tx.setTextColor(Color.parseColor(transColor[0]))
                method1Img.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(transColor[0])))

                if(getScheduleResponse.data.pathInfo.subPath.size > 2) {
                    val walkParam2 = walk2.getLayoutParams() as LinearLayout.LayoutParams
                    walkParam2.weight = getScheduleResponse.data.pathInfo.subPath[2].sectionTime.toFloat()
                    walk2.setLayoutParams(walkParam2)
                }else{
                    walk2.visibility = View.GONE
                    method2.visibility = View.GONE
                    walk3.visibility = View.GONE
                    method3.visibility = View.GONE
                    walk4.visibility = View.GONE
                }


                if(getScheduleResponse.data.pathInfo.subPath.size > 3) {
                    val methodParam2 = method2.getLayoutParams() as LinearLayout.LayoutParams
                    methodParam2.weight = getScheduleResponse.data.pathInfo.subPath[3].sectionTime.toFloat()
                    method2.setLayoutParams(methodParam2)
                    method2Tx.text = transText[1]
                    method2Tx.setTextColor(Color.parseColor(transColor[1]))
                    method2Img.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(transColor[1])))
                }else{
                    method2.visibility = View.GONE
                    walk3.visibility = View.GONE
                    method3.visibility = View.GONE
                    walk4.visibility = View.GONE
                }

                if(getScheduleResponse.data.pathInfo.subPath.size > 4){
                    val walkParam3 = walk3.getLayoutParams() as LinearLayout.LayoutParams
                    walkParam3.weight = getScheduleResponse.data.pathInfo.subPath[4].sectionTime.toFloat()
                    walk3.setLayoutParams(walkParam3)
                }else{
                    walk3.visibility = View.GONE
                    method3.visibility = View.GONE
                    walk4.visibility = View.GONE
                }

                if(getScheduleResponse.data.pathInfo.subPath.size > 5) {
                    val methodParam3 = method3.getLayoutParams() as LinearLayout.LayoutParams
                    methodParam3.weight = getScheduleResponse.data.pathInfo.subPath[5].sectionTime.toFloat()
                    method3.setLayoutParams(methodParam3)
                    method3Tx.text = transText[2]
                    method3Tx.setTextColor(Color.parseColor(transColor[2]))
                    method3Img.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(transColor[2])))
                }else{
                    method3.visibility = View.GONE
                    walk4.visibility = View.GONE
                }

                if(getScheduleResponse.data.pathInfo.subPath.size > 6) {
                    val walkParam4 = walk4.getLayoutParams() as LinearLayout.LayoutParams
                    walkParam4.weight = getScheduleResponse.data.pathInfo.subPath[6].sectionTime.toFloat()
                    walk4.setLayoutParams(walkParam4)
                }else{
                    walk4.visibility = View.GONE
                }

                //알림 표시
                when(getScheduleResponse.data.scheduleInfo.noticeMin) {
                    5,10,20 -> scheNoti.text = String.format("배차 %d분 ", getScheduleResponse.data.scheduleInfo.noticeMin)
                    0 -> scheNoti.text = "알림 없음"
                }

                //알림 범위 표시
                when(getScheduleResponse.data.scheduleInfo.arriveCount) {
                    1 -> scheNotiRange.text = "마지막 배차만"
                    2 -> scheNotiRange.text = "1대 전 배차부터"
                    3 -> scheNotiRange.text = "2대 전 배차부터"
                    4 -> scheNotiRange.text = "3대 전 배차부터"
                }

                //알림반복 표시
                var weekArray = getScheduleResponse.data.weekdayInfo

                for(i in 0..weekArray.size -1){
                    when(weekArray[i]){
                        0-> sun.visibility = VISIBLE
                        1-> mon.visibility = VISIBLE
                        2-> tue.visibility = VISIBLE
                        3-> wed.visibility = VISIBLE
                        4-> thu.visibility = VISIBLE
                        5-> fri.visibility = VISIBLE
                        6-> sat.visibility = VISIBLE
                    }
                }

            }

        })

    }

    fun setButton(){
        act_schedule_complete_iv_back.setOnClickListener {
            finish()
        }
    }
}