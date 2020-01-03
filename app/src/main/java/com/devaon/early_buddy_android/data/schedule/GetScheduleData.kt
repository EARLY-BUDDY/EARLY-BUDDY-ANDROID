package com.devaon.early_buddy_android.data.schedule

import com.google.gson.annotations.SerializedName

data class GetScheduleData(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("data")
    val data: ScheduleData
)

data class ScheduleData(
    @SerializedName("scheduleInfo")
    val scheduleInfo: ScheduleInfo,
    @SerializedName("weekdayInfo")
    val weekdayInfo: Array<Int>,
    @SerializedName("path")
    val pathInfo: PathInfo
)

data class ScheduleInfo(

    @SerializedName("scheduleIdx")
    val scheduleIdx: Int,
    @SerializedName("scheduleName")
    val scheduleName: String,
    @SerializedName("scheduleStartTime")
    val scheduleStartTime: String,
    @SerializedName("startAddress")
    val startAddress: String,
    @SerializedName("startLongitude")
    val startLongitude: Double,
    @SerializedName("endAddress")
    val endAddress: String,
    @SerializedName("endLongitude")
    val endLongitude: Double,
    @SerializedName("noticeMin")
    val noticeMin: Int,
    @SerializedName("arriveCount")
    val arriveCount: Int,
    @SerializedName("scheduleStartDay")
    val scheduleStartDay: String
)

data class PathInfo(
    @SerializedName("pathIdx")
    val pathIdx: Int,
    @SerializedName("pathType")
    val pathType: Int,
    @SerializedName("totalTime")
    val totalTime: Int,
    @SerializedName("totalPay")
    val totalPay: Int,
    @SerializedName("totalWalkTime")
    val totalWalkTime: Int,
    @SerializedName("transitCount")
    val transitCount: Int,
    @SerializedName("subPath")
    val subPath: ArrayList<SubPath>
)

data class SubPath(
    @SerializedName("detailIdx")
    val detailIdx: Int,
    @SerializedName("trafficType")
    val trafficType: Int,
    @SerializedName("distance")
    val distance: Int,
    @SerializedName("sectionTime")
    val sectionTime: Int,
    @SerializedName("stationCount")
    val stationCount: Int,
    @SerializedName("detailStartAddress")
    val detailStartAddress: String,
    @SerializedName("detailEndAddress")
    val detailEndAddress: String,
    @SerializedName("subwayLane")
    val subwayLane: Int,
    @SerializedName("busNo")
    val busNo: String,
    @SerializedName("busType")
    val busType: Int,
    @SerializedName("clicked")
    var clicked : Boolean,
    @SerializedName("stops")
    val stops: ArrayList<String>
)