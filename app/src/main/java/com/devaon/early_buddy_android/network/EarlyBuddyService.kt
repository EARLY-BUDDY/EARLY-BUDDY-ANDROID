package com.devaon.early_buddy_android.network

import com.devaon.early_buddy_android.data.calendar.CalendarResponse
import com.devaon.early_buddy_android.data.place.PlaceResponse
import com.devaon.early_buddy_android.data.route.RouteResponse
import com.devaon.early_buddy_android.data.schedule.GetScheduleData
import com.devaon.early_buddy_android.data.schedule.HomeScheduleResponse
import com.devaon.early_buddy_android.data.schedule.PostScheduleData
import com.devaon.early_buddy_android.data.user.UserResponse
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

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


    @POST("/users/signup")
    fun postSignupUser(
        @Body() body:JsonObject
    ): Call<UserResponse>

    @POST("/users/signin")
    fun postSigninUser(
        @Body() body:JsonObject
    ): Call<UserResponse>

    @POST("/users/setUserName")
    fun postNicknameUser(
        @Body() body:JsonObject
    ): Call<UserResponse>

    @GET("/searchAddress")
    fun getSearchAddress(
        @Query("addr") addr: String
    ): Call<PlaceResponse>

    @POST("/schedules")
    fun postSchedule(
        @Body() body:JsonObject
    ): Call<PostScheduleData>

    @GET("/schedules")
    fun getSchedule(
        @Query("scheduleIdx") scheduleIdx: Int
    ): Call<GetScheduleData>
}
