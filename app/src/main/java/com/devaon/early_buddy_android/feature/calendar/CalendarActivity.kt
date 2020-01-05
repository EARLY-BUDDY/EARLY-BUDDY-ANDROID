package com.devaon.early_buddy_android.feature.calendar

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.calendar.CalendarResponse
import com.devaon.early_buddy_android.data.calendar.Schedule
import com.devaon.early_buddy_android.data.db.Information
import com.devaon.early_buddy_android.feature.calendar.CalendarActivity.getCalendarAcitivityObject.position
import com.devaon.early_buddy_android.feature.schedule.ScheduleActivity
import com.devaon.early_buddy_android.feature.schedule.ScheduleCompleteActivity
import com.devaon.early_buddy_android.network.EarlyBuddyServiceImpl
import com.devaon.early_buddy_android.util.view.WrapContentLinearLayoutManager
import kotlinx.android.synthetic.main.activity_calendar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.Locale.filter
import kotlin.collections.ArrayList


class CalendarActivity : AppCompatActivity() {

    object getCalendarAcitivityObject{
        lateinit var calendarPagerAdapter: CalendarPagerAdapter
        var COUNT_PAGE = 60
        var position = COUNT_PAGE
    }

    lateinit var calendarScheduleRecyclerViewAdapter : CalendarScheduleRecyclerViewAdapter
    lateinit var scheduleList : ArrayList<Schedule>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        act_calendar_cl_header.bringToFront()

        setCalendarVp()
        getScheduleList(Information.idx)
        setButton()

    }

    fun setCalendarVp(){
        getCalendarAcitivityObject.calendarPagerAdapter = CalendarPagerAdapter(supportFragmentManager).apply { setNumOfMonth(60)}

        act_calendar_tv_month.text = getCalendarAcitivityObject.calendarPagerAdapter.getMonthDisplayed(getCalendarAcitivityObject.position)
        act_calendar_vp.run {
            adapter = getCalendarAcitivityObject.calendarPagerAdapter
            currentItem = getCalendarAcitivityObject.COUNT_PAGE
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                }

                override fun onPageSelected(position: Int) {

                    act_calendar_tv_month.text =
                        getCalendarAcitivityObject.calendarPagerAdapter.getMonthDisplayed(position)

                    if (position == 0) {
                        getCalendarAcitivityObject.calendarPagerAdapter.addPrev()
                        setCurrentItem(getCalendarAcitivityObject.COUNT_PAGE, false)
                    } else if (position == getCalendarAcitivityObject.calendarPagerAdapter.count - 1) {
                        getCalendarAcitivityObject.calendarPagerAdapter.addNext()
                        setCurrentItem(getCalendarAcitivityObject.calendarPagerAdapter.count - (getCalendarAcitivityObject.COUNT_PAGE + 1), false)
                    }
                    getCalendarAcitivityObject.position = position
                }

                override fun onPageScrollStateChanged(state: Int) {

                }
            })
        }

        Handler().postDelayed(Runnable { act_calendar_schedule_rv.scrollToPosition(position) }, 200)
    }

    private fun getScheduleList(userIdx: Int){
        val callCalendar: Call<CalendarResponse> = EarlyBuddyServiceImpl.service.getCalendar(
            userIdx,
            getCalendarAcitivityObject.calendarPagerAdapter.getYear(getCalendarAcitivityObject.position),
            getCalendarAcitivityObject.calendarPagerAdapter.getMonth(getCalendarAcitivityObject.position)
        )

        callCalendar.enqueue(object : Callback<CalendarResponse> {
            override fun onFailure(call: Call<CalendarResponse>, t: Throwable) {
                Log.e("error is ", t.toString())
            }

            override fun onResponse(
                call: Call<CalendarResponse>,
                response: Response<CalendarResponse>
            ) {
                if (response.isSuccessful) {
                    Log.e("result is ", response.body().toString())
                    scheduleList = response.body()!!.data.schedules

                    calendarScheduleRecyclerViewAdapter =
                        CalendarScheduleRecyclerViewAdapter(this@CalendarActivity)
                    calendarScheduleRecyclerViewAdapter.setOnScheduleClickListener(onScheduleClickListener)
                    var today = Date()
                    setScheduleRv(String.format("%02d",today.date))


                    act_calendar_schedule_rv.adapter = calendarScheduleRecyclerViewAdapter
                    act_calendar_schedule_rv.layoutManager = LinearLayoutManager(this@CalendarActivity)

                    val animation = AnimationUtils.loadLayoutAnimation(this@CalendarActivity, R.anim.layout_animation_fall_down)
                    act_calendar_schedule_rv.layoutAnimation = animation

                }
            }
        })

    }

    fun setScheduleRv(date: String) {

        var scheduleListInstance : ArrayList<Schedule>
        scheduleListInstance = scheduleList
        var list :ArrayList<Schedule> = ArrayList()

        for(i in  0..scheduleListInstance.size-1){
            if(scheduleListInstance[i].scheduleStartTime.substring(8,10).equals(date) &&
                scheduleListInstance[i].scheduleStartTime.substring(5,7).equals(getCalendarAcitivityObject.calendarPagerAdapter.getMonth(getCalendarAcitivityObject.position)) &&
                scheduleListInstance[i].scheduleStartTime.substring(0,4).equals(getCalendarAcitivityObject.calendarPagerAdapter.getYear(getCalendarAcitivityObject.position))) {
                list.add(scheduleListInstance[i])
            }
        }
        calendarScheduleRecyclerViewAdapter.replaceAll(list)
        calendarScheduleRecyclerViewAdapter.notifyDataSetChanged()
        act_calendar_schedule_rv.scheduleLayoutAnimation()

        if(list.size == 0){
            act_calendar_ll_no_plan.visibility = VISIBLE
        }else
            act_calendar_ll_no_plan.visibility = INVISIBLE
    }

    private fun setButton(){
        act_calendar_iv_back.setOnClickListener {
            finish()
        }

        act_calendar_iv_add_schedule.setOnClickListener {
            val intent = Intent(this@CalendarActivity, ScheduleActivity::class.java)
            startActivity(intent)
        }

        act_calendar_iv_month_before.setOnClickListener {
            act_calendar_vp.setCurrentItem( getCalendarAcitivityObject.position -1 , true)
        }

        act_calendar_iv_month_next.setOnClickListener {
            act_calendar_vp.setCurrentItem( getCalendarAcitivityObject.position +1 , true)
        }
    }

    var onScheduleClickListener
            = object : CalendarScheduleRecyclerViewAdapter.onScheduleClickListener{
        override fun onItemClick(scheduleIdx: Int) {
            var intent = Intent(this@CalendarActivity, ScheduleCompleteActivity::class.java)
            intent.putExtra("scheduleIdx", scheduleIdx)
            startActivity(intent)
        }

    }


}
