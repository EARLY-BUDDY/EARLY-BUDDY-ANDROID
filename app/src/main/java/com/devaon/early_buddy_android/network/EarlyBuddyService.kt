package com.devaon.early_buddy_android.network

import com.devaon.early_buddy_android.data.calendar.CalendarResponse
import com.devaon.early_buddy_android.data.route.RouteResponse
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

    @GET(value = "/calenders")
    fun getCalendar(
        @Query("userIdx") userIdx : Int,
        @Query("year") year : String,
        @Query("month") month : String
    ): Call<CalendarResponse>
}