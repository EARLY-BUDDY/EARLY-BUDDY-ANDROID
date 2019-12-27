package com.devaon.early_buddy_android.feature.calendar

import android.content.Context
import android.graphics.Color
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.place.calendar.Schedule
import java.util.*


class CalendarScheduleRecyclerViewAdapter(
    val context: Context,
    var dataList: ArrayList<Schedule>
) : RecyclerView.Adapter<CalendarScheduleRecyclerViewAdapter.Holder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CalendarScheduleRecyclerViewAdapter.Holder {
        val view = LayoutInflater.from(context)!!.inflate(R.layout.item_calendar_date_schedule, viewGroup, false)
        return Holder(view)
    }

    override fun getItemCount() = 2

    override fun onBindViewHolder(holder: Holder, position: Int) {

       holder.title.text = dataList[position].title
        holder.place.text = dataList[position].place
        holder.time.text = dataList[position].time
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var title = itemView.findViewById(R.id.item_calendar_date_schedule_tv_title) as TextView
        var place = itemView.findViewById(R.id.item_calendar_date_schedule_tv_place) as TextView
        var time = itemView.findViewById(R.id.item_calendar_date_schedule_tv_time) as TextView
        //var arrow = itemView.findViewById(R.id.item_calendar_date_schedule_iv_arrow) as ImageView
    }

}
