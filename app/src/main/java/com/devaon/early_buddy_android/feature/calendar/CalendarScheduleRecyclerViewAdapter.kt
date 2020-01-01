package com.devaon.early_buddy_android.feature.calendar

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginBottom
import androidx.core.view.marginRight
import androidx.recyclerview.widget.RecyclerView
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.calendar.Schedule
import java.util.*
import kotlin.collections.ArrayList


class CalendarScheduleRecyclerViewAdapter(
    val context: Context,
    var dataList: ArrayList<Schedule>
) : RecyclerView.Adapter<CalendarScheduleRecyclerViewAdapter.Holder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CalendarScheduleRecyclerViewAdapter.Holder {
        val view = LayoutInflater.from(context)!!.inflate(R.layout.item_calendar_date_schedule, viewGroup, false)
        return Holder(view)
    }

    override fun getItemCount() = dataList.size

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.title.text = dataList[position].scheduleName
        holder.place.text = dataList[position].endAddress

        var hour = dataList[position].scheduleStartTime.substring(11, 13)
        if(hour.toInt() > 12) {
            holder.time.text = "오후 " + dataList[position].scheduleStartTime.substring(11, 16)
        }else{
            holder.time.text = "오전 " + dataList[position].scheduleStartTime.substring(11, 16)
        }
    }

    fun replaceAll(list: MutableList<Schedule>){
        dataList.clear()
        dataList.addAll(list)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var title = itemView.findViewById(R.id.item_calendar_date_schedule_tv_title) as TextView
        var place = itemView.findViewById(R.id.item_calendar_date_schedule_tv_place) as TextView
        var time = itemView.findViewById(R.id.item_calendar_date_schedule_tv_time) as TextView

    }

}
