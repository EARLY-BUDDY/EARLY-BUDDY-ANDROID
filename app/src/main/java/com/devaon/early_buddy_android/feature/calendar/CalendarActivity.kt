package com.devaon.early_buddy_android.feature.calendar

import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.calendar.CalendarResponse
import com.devaon.early_buddy_android.data.calendar.Schedule
import com.devaon.early_buddy_android.feature.calendar.CalendarActivity.getCalendarAcitivityObject.position
import com.devaon.early_buddy_android.network.EarlyBuddyServiceImpl
import kotlinx.android.synthetic.main.activity_calendar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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


        setCalendarVp()
        getScheduleList(1)
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

        Log.e("before enquee", "hi")
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
                        CalendarScheduleRecyclerViewAdapter(this@CalendarActivity, scheduleList)
                    calendarScheduleRecyclerViewAdapter.notifyDataSetChanged()

                    act_calendar_schedule_rv.adapter = calendarScheduleRecyclerViewAdapter
                    act_calendar_schedule_rv.layoutManager =
                        LinearLayoutManager(this@CalendarActivity)

                    Log.e("schedule List:", scheduleList?.size.toString())

                }
                Log.e("fail result is ", response.body().toString())
            }
        })

    }


    private fun setButton(){
        act_calendar_iv_back.setOnClickListener {
            finish()
        }
    }



}
