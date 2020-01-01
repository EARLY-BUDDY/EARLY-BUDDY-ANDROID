package com.devaon.early_buddy_android.network

import com.devaon.early_buddy_android.data.calendar.CalendarResponse
import com.devaon.early_buddy_android.data.route.RouteResponse
import com.devaon.early_buddy_android.data.schedule.HomeScheduleResponse
import com.devaon.early_buddy_android.data.user.GetUserData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EarlyBuddyService {

    @GET(value = "/searchPath")
    fun getRoute(
        @Query("SX") SX: Double,
        @Query("SY") SY: Double,
        @Query("EX") EX: Double,
        @Query("EY") EY: Double,
        @Query("SearchPathType") SearchPathType: Int

    ): Call<RouteResponse>

    @GET(value = "/home")
    fun getHomeSchedule(
        @Query("userIdx") userIdx: Int
    ): Call<HomeScheduleResponse>

    @GET(value = "/calenders")
    fun getCalendar(
        @Query("userIdx") userIdx: Int,
        @Query("year") year: String,
        @Query("month") month: String
    ): Call<CalendarResponse>

    @GET("/users/{id}")
    fun getUser(
        @Path("id") id: String
    ): Call<GetUserData>
}
