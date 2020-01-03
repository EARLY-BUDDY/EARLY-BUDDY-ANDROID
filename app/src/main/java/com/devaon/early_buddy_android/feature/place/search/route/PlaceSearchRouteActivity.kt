package com.devaon.early_buddy_android.feature.place.search.route

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.route.Path
import com.devaon.early_buddy_android.data.route.RouteResponse
import com.devaon.early_buddy_android.feature.place.search.text.PlaceDirectionsActivity
import com.devaon.early_buddy_android.feature.route.RouteActivity
import com.devaon.early_buddy_android.feature.schedule.ScheduleActivity
import com.devaon.early_buddy_android.network.EarlyBuddyServiceImpl
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_place_search_route.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaceSearchRouteActivity : AppCompatActivity(){

    var placeSearchRouteRecyclerViewAdapter =  PlaceSearchRouteAdapter(this)

    private var searchPathType = 0
    lateinit var routes : ArrayList<Path>

    var date = ""
    var dayOfWeek = 0
    var time = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_search_route)

        date = intent.getStringExtra("scheduleDate")!!
        dayOfWeek = intent.getIntExtra("scheduleDayOfWeek", 8)
        time = intent.getStringExtra("scheduleTime")!!

        setRv()
        setText()
        setButton()
    }

    override fun onResume() {
        super.onResume()

        if(RouteActivity.Route.isSelected){
            finish()
        }

        if(ScheduleActivity.schedulePlace.startPlaceName != "" && ScheduleActivity.schedulePlace.endPlaceName != "") {
            getRouteResults(ScheduleActivity.schedulePlace.startPlaceX, ScheduleActivity.schedulePlace.startPlaceY,
                ScheduleActivity.schedulePlace.endPlaceX, ScheduleActivity.schedulePlace.endPlaceY)
            act_place_search_route_cl_background.setBackgroundColor(Color.parseColor("#f5f5f5"))
            act_place_search_route_cl_gray.visibility = VISIBLE

            act_place_search_route_et_engine_from.text = ScheduleActivity.schedulePlace.startPlaceName
            act_place_search_route_et_engine_from.setTextColor(Color.parseColor("#313131"))
            act_place_search_route_et_engine_to.text = ScheduleActivity.schedulePlace.endPlaceName
            act_place_search_route_et_engine_to.setTextColor(Color.parseColor("#313131"))
        }
    }

    private fun setRv(){
        placeSearchRouteRecyclerViewAdapter.setOnPlaceSearchRouteClickListener(onPlaceSearchRouteClickListener)
        act_place_search_route_rv.adapter = placeSearchRouteRecyclerViewAdapter
        act_place_search_route_rv.layoutManager = LinearLayoutManager(this)
    }

    private fun setText(){

        var week =""
        when(dayOfWeek){
            1 -> week = "일요일"
            2 -> week = "월요일"
            3 -> week = "화요일"
            4 -> week = "수요일"
            5 -> week = "목요일"
            6 -> week = "금요일"
            7 -> week = "토요일"
            8 -> week ="알 수 없음"
        }

        week = " ("+week+')'

        if(date[0] == '0' && date[4] == '0'){
            act_place_search_route_tv_sche_day.text = date.substring(1,4) + date.substring(5, 7) + week
        }else if(date[0] == '0'){
            act_place_search_route_tv_sche_day.text = date.substring(1,7) + week
        }else if(date[4] == '0'){
            act_place_search_route_tv_sche_day.text = date.substring(0,4) + date.substring(5, 7) + week
        }else{
            act_place_search_route_tv_sche_day.text = date +week
        }


        if(time[0] == 'P') {
            act_place_search_route_tv_sche_time.text = "오후" + time.substring(2, 8)
        }else{
            act_place_search_route_tv_sche_time.text = "오전" + time.substring(2, 8)
        }


    }

    // 통신
    private fun getRouteResults(sx: Double, sy: Double, ex: Double, ey: Double) {
        val callRoute: Call<RouteResponse> = EarlyBuddyServiceImpl.service.getRoute(
            sx, sy, ex, ey,
            searchPathType
        )

        callRoute.enqueue(object : Callback<RouteResponse> {
            override fun onFailure(call: Call<RouteResponse>, t: Throwable) {
                Log.e("callRoute error : ", t.toString())
            }

            override fun onResponse(call: Call<RouteResponse>, response: Response<RouteResponse>) {
                if (response.isSuccessful) {
                    Log.e("callRoute result : ", response.body().toString())
                    routes = response.body()!!.data.path

                    placeSearchRouteRecyclerViewAdapter.replaceAll(routes)
                    placeSearchRouteRecyclerViewAdapter.notifyDataSetChanged()

                    if(routes.size > 0){
                        act_place_select_iv_bird.visibility = INVISIBLE
                    }else
                        act_place_select_iv_bird.visibility = VISIBLE
                }
            }
        })
    }

    val onPlaceSearchRouteClickListener
            = object : PlaceSearchRouteAdapter.onPlaceSearchRouteClickListener{
        override fun onItemClickListener(position: Int) {
            ScheduleActivity.selectedPath.path = routes[position]

            val gson = Gson()
            val path = gson.toJson(ScheduleActivity.selectedPath.path)

            Log.e("path", path)

            val intent = Intent(this@PlaceSearchRouteActivity, RouteActivity::class.java)
            startActivity(intent)

        }
    }

    private fun setButton(){
        act_place_select_iv_back.setOnClickListener {
            finish()
        }
        act_place_search_route_cl_engine_from.setOnClickListener {
            var intent = Intent(this, PlaceDirectionsActivity::class.java)
            intent.putExtra("click", "from")
            startActivity(intent)
            overridePendingTransition(0,0)
        }
        act_place_search_route_et_engine_from.setOnClickListener{
            var intent = Intent(this, PlaceDirectionsActivity::class.java)
            intent.putExtra("click", "from")
            startActivity(intent)
            overridePendingTransition(0,0)
        }
        act_place_search_route_cl_engine_to.setOnClickListener {
            var intent = Intent(this, PlaceDirectionsActivity::class.java)
            intent.putExtra("click", "to")
            startActivity(intent)
            overridePendingTransition(0,0)
        }
        act_place_search_route_et_engine_to.setOnClickListener{
            var intent = Intent(this, PlaceDirectionsActivity::class.java)
            intent.putExtra("click", "to")
            startActivity(intent)
            overridePendingTransition(0,0)
        }
    }
}