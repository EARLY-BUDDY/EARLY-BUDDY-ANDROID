package com.devaon.early_buddy_android.feature.schedule

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
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

class ScheduleCompleteActivity: AppCompatActivity(){

    lateinit var scheName: TextView
    lateinit var scheDay: TextView
    lateinit var scheTime: TextView
    lateinit var scheFrom: TextView
    lateinit var scheTo: TextView
    lateinit var scheNoti: TextView
    lateinit var scheNotiRange: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_complete)

        scheName = findViewById(R.id.act_schedule_complete_tv_sche_name)
        scheDay = findViewById(R.id.act_schedule_complete_tv_sche_day)
        scheTime = findViewById(R.id.act_schedule_complete_tv_sche_time)
        scheFrom = findViewById(R.id.act_schedule_complete_complete_tv_from)
        scheTo = findViewById(R.id.act_schedule_complete_complete_tv_to)
        scheNoti = findViewById(R.id.act_schedule_complete_complete_tv_noti_pick)
        scheNotiRange = findViewById(R.id.act_schedule_complete_tv_noti_range_pick)

        val scheduleIdx = intent.getIntExtra("scheduleIdx", 0)
        Log.e("schedule", scheduleIdx.toString())

        getSchedule()
    }

    private fun getSchedule(){

        val callGetSchedule: Call<GetScheduleData> = EarlyBuddyServiceImpl.service.getSchedule(70)

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

                scheName.text = getScheduleResponse.data.scheduleInfo.scheduleName
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

                scheFrom.text = getScheduleResponse.data.scheduleInfo.startAddress
                scheTo.text = getScheduleResponse.data.scheduleInfo.endAddress

                when(getScheduleResponse.data.scheduleInfo.noticeMin) {
                    5,10,20 -> scheNoti.text = String.format("배차 %d분 ", getScheduleResponse.data.scheduleInfo.noticeMin)
                    0 -> scheNoti.text = "알림 없음"
                }

                when(getScheduleResponse.data.scheduleInfo.arriveCount) {
                    1 -> scheNotiRange.text = "마지막 배차만"
                    2 -> scheNotiRange.text = "1대 전 배차부터"
                    3 -> scheNotiRange.text = "2대 전 배차부터"
                    4 -> scheNotiRange.text = "3대 전 배차부터"
                }

            }

        })

    }
}