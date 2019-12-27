package com.devaon.early_buddy_android.feature.calendar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.devaon.early_buddy_android.R
import kotlinx.android.synthetic.main.activity_calendar.*


class CalendarActivity : AppCompatActivity() {

    lateinit var calendarPagerAdapter: CalendarPagerAdapter
    private var COUNT_PAGE = 60
    private var position = COUNT_PAGE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        setCalendarVp()
    }

    fun setCalendarVp(){
        calendarPagerAdapter = CalendarPagerAdapter(supportFragmentManager).apply { setNumOfMonth(60)}

        act_calendar_tv_month.text = calendarPagerAdapter.getMonthDisplayed(position)
        act_calendar_vp.run {
            adapter = calendarPagerAdapter
            currentItem = COUNT_PAGE
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                }

                override fun onPageSelected(position: Int) {

                    act_calendar_tv_month.text =
                        calendarPagerAdapter.getMonthDisplayed(position)

                    if (position == 0) {
                        calendarPagerAdapter.addPrev()
                        setCurrentItem(COUNT_PAGE, false)
                    } else if (position == calendarPagerAdapter.count - 1) {
                        calendarPagerAdapter.addNext()
                        setCurrentItem(calendarPagerAdapter.count - (COUNT_PAGE + 1), false)
                    }
                    this@CalendarActivity.position = position
                }

                override fun onPageScrollStateChanged(state: Int) {

                }
            })
        }
    }
}
