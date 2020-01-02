package com.devaon.early_buddy_android.feature.place.search.route

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.route.RouteResponse
import com.devaon.early_buddy_android.network.EarlyBuddyServiceImpl
import kotlinx.android.synthetic.main.activity_place_search_route.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaceSearchRouteActivity : AppCompatActivity(){

    var placeSearchRouteRecyclerViewAdapter =  PlaceSearchRouteAdapter(this)

    private var startAdress = ""
    private var startX = 127.08282465301149
    private var startY = 37.62072502768881

    private var endAddress = ""
    private var endX = 127.03746391719882
    private var endY = 37.4720040276288

    private var searchPathType = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_search_route)

        setRv()
        getRouteResults()
    }

    private fun setRv(){
        act_place_search_route_rv.adapter = placeSearchRouteRecyclerViewAdapter
        act_place_search_route_rv.layoutManager = LinearLayoutManager(this)
    }

    // 통신
    private fun getRouteResults() {
        val callRoute: Call<RouteResponse> = EarlyBuddyServiceImpl.service.getRoute(
            startX, startY,
            endX, endY,
            searchPathType
        )

        callRoute.enqueue(object : Callback<RouteResponse> {
            override fun onFailure(call: Call<RouteResponse>, t: Throwable) {
                Log.e("callRoute error : ", t.toString())
            }

            override fun onResponse(call: Call<RouteResponse>, response: Response<RouteResponse>) {
                if (response.isSuccessful) {
                    Log.e("callRoute result : ", response.body().toString())
                    val routes = response.body()!!.data.path

                    placeSearchRouteRecyclerViewAdapter.replaceAll(routes)
                    placeSearchRouteRecyclerViewAdapter.notifyDataSetChanged()
                }
            }
        })
    }


    /** address setter **/
    fun setStartAddress(address: String, x: Double, y: Double){
        startAdress = address
        startX = x
        startY = y
    }

    fun setEndAddress(address: String, x: Double, y: Double){
        endAddress = address
        endX = x
        endY = y
    }
}