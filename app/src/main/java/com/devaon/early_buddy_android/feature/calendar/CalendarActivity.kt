package com.devaon.early_buddy_android.feature.calendar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.devaon.early_buddy_android.R
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
}
