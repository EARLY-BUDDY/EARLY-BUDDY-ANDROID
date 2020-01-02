package com.devaon.early_buddy_android.data.schedule

import com.devaon.early_buddy_android.data.route.SubPath
import com.google.gson.annotations.SerializedName

data class PostScheduleData(
    @SerializedName("scheduleName")
    val scheduleName: String,
    @SerializedName("scheduleStartTime")
    val scheduleStartTime: String,
    @SerializedName("startAddress")
    val startAddress: String,
    @SerializedName("startLongitude")
    val startLongitude: Double,
    @SerializedName("startLatitude")
    val startLatitude: Double,
    @SerializedName("endAddress")
    val endAddress: String,
    @SerializedName("endLongitude")
    val endLongitude:Double,
    @SerializedName("endLatitude")
    val endLatitude:Double,
    @SerializedName("arriveCount")
    val arriveCount: Int,
    @SerializedName("noticeMin")
    val noticeMin: Int,
    @SerializedName("weekdays")
    val weekdays: ArrayList<Int>,
    @SerializedName("userIdx")
    val userIdx: Int,
    @SerializedName("path")
    val userPath: UserPath
)

data class UserPath(
    @SerializedName("pathType")     // 1 -> 지하철, 2 -> 버스, 3 -> 버스+지하철
    val pathType: Int,
    @SerializedName("totalTime")
    val totalTime: Int,
    @SerializedName("totalPay")
    val totalPay: Int,
    @SerializedName("transitCount")
    val transitCount: Int,
    @SerializedName("totalWalkTime")
    val totalWalkTime: Int,
    @SerializedName("subPath")
    val subPath: ArrayList<SubPath>
)
