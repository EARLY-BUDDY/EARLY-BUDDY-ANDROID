package com.devaon.early_buddy_android.data.calendar
import com.google.gson.annotations.SerializedName

data class CalendarResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("data")
    val data: Calendar
)

data class Calendar(
    @SerializedName("year")
    val year: String,
    @SerializedName("month")
    val month: String,
    @SerializedName("schedules")
    val schedules: ArrayList<Schedule>
)

data class Schedule(
    @SerializedName("scheduleIdx")
    val scheduleIdx : Int,
    @SerializedName("scheduleName")
    val scheduleName: String,
    @SerializedName("scheduleStartTime")
    val scheduleStartTime: String,
    @SerializedName("endAddress")
    val endAddress: String,
    var date : Date
)