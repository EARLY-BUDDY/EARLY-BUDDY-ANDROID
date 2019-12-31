package com.devaon.early_buddy_android.network

import com.devaon.early_buddy_android.data.route.RouteResponse
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
        @Query("EY") EY: Double
    ): Call<RouteResponse>


    @GET("/users/{id}")
    fun getUser(
        @Path("id") id: String
    ): Call<GetUserData>
}