package com.devaon.early_buddy_android.feature.calendar

import android.graphics.Color
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.devaon.early_buddy_android.R
import java.util.*


class CalendarPageRecyclerViewAdapter(
    val baseCalendar: BaseCalendar,
    val calendarFragment: CalendarPageFragment
) : RecyclerView.Adapter<CalendarPageRecyclerViewAdapter.Holder>() {

    private var mSelectedItems : SparseBooleanArray = SparseBooleanArray(0)

    init{
        baseCalendar.initBaseCalendar {
            refreshView(it)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CalendarPageRecyclerViewAdapter.Holder {
        val view = LayoutInflater.from(calendarFragment.context)!!.inflate(R.layout.item_calendar_date, viewGroup, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = BaseCalendar.LOW_OF_CALENDAR * BaseCalendar.DAYS_OF_WEEK

    override fun onBindViewHolder(holder: Holder, position: Int) {

        if(position % BaseCalendar.DAYS_OF_WEEK == 0)
            holder.date.setTextColor(Color.parseColor("#ff1200"))
        else if(position % BaseCalendar.DAYS_OF_WEEK == 6)
            holder.date.setTextColor(Color.parseColor("#3092ff"))
        else
            holder.date.setTextColor(Color.parseColor("#676d6e"))

        if (position < baseCalendar.prevMonthTailOffset
            || position >= baseCalendar.prevMonthTailOffset + baseCalendar.currentMonthMaxDate) {
            holder.date.alpha = 0.3f
            holder.container.isClickable = false
        } else {
            holder.date.alpha = 1f
        }

        if(baseCalendar.data[position].hasSchedule){
            holder.schedule.visibility = View.VISIBLE
        }

        holder.date.text = baseCalendar.data[position].date
        if(baseCalendar.data[position].isToDay){
            holder.container.setBackgroundResource(R.drawable.circle_c3c3c3)
            holder.date.setTextColor(Color.parseColor("#ffffff"))
            holder.schedule.visibility = View.INVISIBLE
        }

        holder.container.setOnClickListener {

            var fragmentPosition = calendarFragment.fragmentPosition

            CalendarActivity.getCalendarAcitivityObject.calendarPagerAdapter.frgMap[fragmentPosition-1]?.calendarPageRecyclerViewAdapter?.clearSelectedItem()
            CalendarActivity.getCalendarAcitivityObject.calendarPagerAdapter.frgMap[fragmentPosition+1]?.calendarPageRecyclerViewAdapter?.clearSelectedItem()
            clearSelectedItem()
            toggleItemSelected(position)
        }

        if(isItemSelected(position)){
            holder.container.setBackgroundResource(R.drawable.circle_3092ff)
            holder.date.setTextColor(Color.parseColor("#ffffff"))
        }else if(!baseCalendar.data[position].isToDay){
            holder.container.setBackgroundColor(Color.parseColor("#ffffff"))
            if(position % BaseCalendar.DAYS_OF_WEEK == 0)
                holder.date.setTextColor(Color.parseColor("#ff1200"))
            else if(position % BaseCalendar.DAYS_OF_WEEK == 6)
                holder.date.setTextColor(Color.parseColor("#3092ff"))
            else
                holder.date.setTextColor(Color.parseColor("#676d6e"))
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var container = itemView.findViewById(R.id.item_calendar_date_container) as ConstraintLayout
        var date = itemView.findViewById(R.id.item_calendar_date_tv) as TextView
        var schedule = itemView.findViewById(R.id.item_Calendar_date_iv_schedule) as ImageView
    }

    private fun toggleItemSelected(position: Int){
        if (mSelectedItems.get(position, false) === true) {
            mSelectedItems.delete(position)
            notifyItemChanged(position)
        } else {
            mSelectedItems.put(position, true)
            notifyItemChanged(position)
        }
    }

    private fun isItemSelected(position: Int) : Boolean{
        return mSelectedItems.get(position, false)
    }

    fun clearSelectedItem(){
        var position = 0

        for(i in  0..mSelectedItems.size()-1){
            position = mSelectedItems.keyAt(i)
            mSelectedItems.put(position, false)
            notifyItemChanged(position)
        }
        mSelectedItems.clear()

    }

    private fun getPrevMonth(){

    }

    fun changeToPrevMonth() {
        baseCalendar.changeToPrevMonth {
            refreshView(it)
        }
    }

    fun changeToNextMonth() {
        baseCalendar.changeToNextMonth {
            refreshView(it)
        }
    }

    private fun refreshView(calendar: Calendar) {
        notifyDataSetChanged()
    }
}
