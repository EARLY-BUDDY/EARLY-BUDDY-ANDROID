package com.devaon.early_buddy_android.feature.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.db.Information
import com.devaon.early_buddy_android.data.schedule.HomeScheduleResponse
import com.devaon.early_buddy_android.feature.calendar.CalendarActivity
import com.devaon.early_buddy_android.feature.schedule.ScheduleActivity
import com.devaon.early_buddy_android.network.EarlyBuddyServiceImpl
import com.devaon.early_buddy_android.util.view.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_no_schedule.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoScheduleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_schedule)

        intent()
    }

    override fun onResume() {
        super.onResume()
        val callRoute: Call<HomeScheduleResponse> = EarlyBuddyServiceImpl.service.getHomeSchedule(
            Information.idx)

        callRoute.enqueue(object : Callback<HomeScheduleResponse> {
            override fun onFailure(call: Call<HomeScheduleResponse>, t: Throwable) {
                Log.e("error is ",t.message)
            }

            override fun onResponse(
                call: Call<HomeScheduleResponse>,
                response: Response<HomeScheduleResponse>
            ) {
                var homeScheduleResponse = response.body()!!
                if(homeScheduleResponse.message == "홈 화면에 보여줄 일정이 없습니다"){

                }
                else{
                    var intent = Intent(this@NoScheduleActivity,HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        })

    }
    private fun intent() {
        act_no_iv_planner.setSafeOnClickListener {
            var goToPlanner = Intent(this, CalendarActivity::class.java)
            startActivity(goToPlanner)
        }


        act_no_iv_plus.setOnClickListener {
            var goToAddSchedule = Intent(this, ScheduleActivity::class.java)
            startActivity(goToAddSchedule)
        }
        act_no_tv_add_sch.setOnClickListener {
            var goToAddSchedule = Intent(this, ScheduleActivity::class.java)
            startActivity(goToAddSchedule)
        }
    }

}
