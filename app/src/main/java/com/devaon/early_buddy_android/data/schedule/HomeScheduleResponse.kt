package com.devaon.early_buddy_android.data.schedule

import com.google.gson.annotations.SerializedName
import java.sql.Time

data class HomeScheduleResponse(
    @SerializedName("success")
    val success:Boolean,
    @SerializedName("message")
    val message:String,
    @SerializedName("data")
    val homeSchedule:HomeSchedule
    )

data class HomeSchedule(
    @SerializedName("ready")
    val ready:Boolean,
    @SerializedName("lastTransCount")
    val lastTransCount:Int,
    @SerializedName("arriveTime")
    val arriveTime:String,
    @SerializedName("firstTrans")
    val firstTrans:FirstTrans,
    @SerializedName("nextTransArriveTime")
    val nextTransArriveTime:String,
    @SerializedName("scheduleSummaryData")
    val scheduleSummaryData:ScheduleSummaryData,
    @SerializedName("isGoing")
    val isGoing:Int
)

data class FirstTrans(
    @SerializedName("detailIdx")
    val detailIdx:Int,
    @SerializedName("trafficType")
    val trafficType:Int,
    @SerializedName("subwayLane")
    val subwayLane:Int?,
    @SerializedName("busNo")
    val busNo:String?,
    @SerializedName("busType")
    val busType:Int?,
    @SerializedName("detailStartAddress")
    val detailStartAddress:String
)
data class ScheduleSummaryData(
    @SerializedName("scheduleIdx")
    val scheduleIdx:Int,
    @SerializedName("scheduleName")
    val scheduleName:String,
    @SerializedName("scheduleStartTime")
    val scheduleStartTime:String,
    @SerializedName("endAddress")
    val endAddress:String
)