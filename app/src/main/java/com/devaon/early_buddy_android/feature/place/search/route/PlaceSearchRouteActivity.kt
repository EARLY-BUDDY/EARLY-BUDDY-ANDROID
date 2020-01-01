package com.devaon.early_buddy_android.feature.place.search.route

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.route.RouteResponse
import com.devaon.early_buddy_android.network.EarlyBuddyServiceImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaceSearchRouteActivity : AppCompatActivity(){

    private lateinit var rvPlaceSearchRoute: RecyclerView
    private lateinit var placeSeachRouteAdapter: PlaceSearchRouteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_search_route)
//        setContentView(R.layout.item_list_place_search_route)
//
        initPlaceSearchRoute()
        makeListItem()
//        var width = findViewById<ImageView>(R.id.li_place_search_route_iv_gray_line).maxWidth
//        Log.e("width!", width.toString())
    }

    private fun initPlaceSearchRoute(){
        rvPlaceSearchRoute = findViewById(R.id.act_place_search_route_rv)
        placeSeachRouteAdapter = PlaceSearchRouteAdapter(this)
        rvPlaceSearchRoute.adapter = placeSeachRouteAdapter
        rvPlaceSearchRoute.layoutManager = LinearLayoutManager(this)
    }

    private fun makeListItem() {
        val callRoute: Call<RouteResponse> = EarlyBuddyServiceImpl.service.getRoute(
            127.08282465301149,
            37.62072502768881,
            127.03746391719882,
            37.4720040276288,
            0
        )


        callRoute.enqueue(object : Callback<RouteResponse> {
            override fun onFailure(call: Call<RouteResponse>, t: Throwable) {
                Log.e("error is ", t.toString())
            }

            override fun onResponse(call: Call<RouteResponse>, response: Response<RouteResponse>) {
                if (response.isSuccessful) {
                    Log.e("result is ", response.body().toString())
                    val route = response.body()!!
//                    var i  = 0
//                    var list : ArrayList<SubPath> =ArrayList()
//                    while (i<route.data.path.size){
//                        list.add()
//                    }
                    placeSeachRouteAdapter.routeList = route.data.path
                    placeSeachRouteAdapter.notifyDataSetChanged()
                }
            }
        })
    }
}