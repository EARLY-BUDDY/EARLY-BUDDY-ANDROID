package com.devaon.early_buddy_android.network

import com.devaon.early_buddy_android.data.route.RouteResponse
import com.devaon.early_buddy_android.data.schedule.HomeScheduleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EarlyBuddyService {

    @GET(value = "/searchPath")
    fun getRoute(
        @Query("SX") SX: Double,
        @Query("SY") SY: Double,
        @Query("EX") EX: Double,
        @Query("EY") EY: Double
    ): Call<RouteResponse>

    @GET(value = "/home")
    fun getHomeSchedule(
        @Query("userIdx") userIdx: Int
    ): Call<HomeScheduleResponse>
}