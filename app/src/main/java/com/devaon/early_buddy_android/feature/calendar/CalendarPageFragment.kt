package com.devaon.early_buddy_android.feature.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.place.calendar.Date
import com.devaon.early_buddy_android.util.view.NonScrollGridLayoutManager
import kotlinx.android.synthetic.main.fragment_calendar_page.*


class CalendarPageFragment : Fragment(){

    private var timeByMillis: Long = 0

    lateinit var calendarPageRecyclerViewAdapter: CalendarPageRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_calendar_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCalendarRv()

    }

    private fun setCalendarRv(){

        val baseCalendar = BaseCalendar(timeByMillis)

        calendarPageRecyclerViewAdapter = CalendarPageRecyclerViewAdapter(baseCalendar, this)
        frag_calendar_rv.run{
            adapter = calendarPageRecyclerViewAdapter
            layoutManager = NonScrollGridLayoutManager(activity!!, 7)
        }
    }

    fun setTimeByMillis(timeByMillis: Long) {
        this.timeByMillis = timeByMillis
    }

    companion object {

        fun newInstance(position: Int): CalendarPageFragment {
            val frg = CalendarPageFragment()
            val bundle = Bundle()
            bundle.putInt("position", position)
            frg.arguments = bundle
            return frg
        }
    }
}
