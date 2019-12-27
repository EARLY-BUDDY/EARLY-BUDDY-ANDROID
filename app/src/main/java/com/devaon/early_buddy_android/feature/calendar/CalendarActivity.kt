package com.devaon.early_buddy_android.feature.calendar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.place.calendar.Date
import com.devaon.early_buddy_android.data.place.calendar.Schedule
import kotlinx.android.synthetic.main.activity_calendar.*


class CalendarActivity : AppCompatActivity() {

    object getCalendarAcitivityObject{
        lateinit var calendarPagerAdapter: CalendarPagerAdapter
        var COUNT_PAGE = 60
        var position = COUNT_PAGE
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        setCalendarVp()
        setScheduleRv()
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
    }

    fun setScheduleRv(){

        var dummyDataList = ArrayList<Schedule>()
        dummyDataList.add(Schedule("SOPT 데모데이", "오후 12:20", "오렌지팜 서초센터"))
        dummyDataList.add(Schedule("SOPT 데모데이2", "오후 12:20", "오렌지팜 서초센터"))

        act_calendar_schedule_rv.adapter = CalendarScheduleRecyclerViewAdapter(this, dummyDataList)
        act_calendar_schedule_rv.layoutManager = LinearLayoutManager(this)
    }
}
