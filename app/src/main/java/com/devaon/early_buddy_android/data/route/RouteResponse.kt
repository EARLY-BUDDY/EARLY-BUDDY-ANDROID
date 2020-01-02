package com.devaon.early_buddy_android.data.route

import com.google.gson.annotations.SerializedName

data class RouteResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("data")
    val data: Route
)

data class Route(
    @SerializedName("path")
    val path: ArrayList<Path>
)

data class Path(
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

data class SubPath(
    @SerializedName("trafficType")  //  1 -> 지하철, 2 -> 버스, 3 -> 걷기
    val trafficType: Int,
    @SerializedName("distance")
    val distance: Int,
    @SerializedName("sectionTime")   //그 구간에 걸리는 시간
    val sectionTime: Int,
    @SerializedName("stationCount")
    val stationCount: Int,
    @SerializedName("lane")
    val lane: Lane,
    @SerializedName("startName")
    var startName: String,
    @SerializedName("startX")
    val startX: Double,
    @SerializedName("startY")
    val startY: Double,
    @SerializedName("endName")
    val endName: String,
    @SerializedName("endX")
    val endX: Double,
    @SerializedName("endY")
    val endY: Double,
    @SerializedName("way")
    val way: String,
    @SerializedName("endExitNo")
    val endExitNo: Int,
    @SerializedName("passStopList")
    val passStopList: PassThroughStation,
    @SerializedName("clicked")
    var clicked: Boolean
)

data class Lane(
    @SerializedName("name")
    val laneName: String,
    @SerializedName("subwayCode")
    val subwayCode: Int,
    @SerializedName("type")
    val type: Int,
    @SerializedName("busNo")
    val busNo: String
)

data class PassThroughStation(
    @SerializedName("stations")
    val stations: ArrayList<Station>
)

data class Station(
    @SerializedName("index")
    val index: Int,
    @SerializedName("stationName")
    val stationName: String
)