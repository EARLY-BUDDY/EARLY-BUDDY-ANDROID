package com.devaon.early_buddy_android.feature.route

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.route.RouteResponse
import com.devaon.early_buddy_android.data.schedule.GetScheduleData
import com.devaon.early_buddy_android.data.schedule.PathInfo
import com.devaon.early_buddy_android.feature.home.HomeRouteAdapter
import com.devaon.early_buddy_android.feature.home.HomeRouteViewHolder
import com.devaon.early_buddy_android.feature.schedule.ScheduleActivity
import com.devaon.early_buddy_android.feature.schedule.ScheduleActivity.schedulePlace.endPlaceName
import com.devaon.early_buddy_android.feature.schedule.ScheduleActivity.schedulePlace.startPlaceName
import com.devaon.early_buddy_android.network.EarlyBuddyServiceImpl
import kotlinx.android.synthetic.main.activity_route.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RouteActivity : AppCompatActivity() {

    private lateinit var routeRecyclerView: RecyclerView
    private lateinit var routeAdapter: RouteAdapter

    object Route{
        var isSelected = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route)

        var min = intent.getIntExtra("totalTime",0)%60
        var hour = intent.getIntExtra("totalTime",0)/60
        act_place_search_route_tv_time.text = String.format("%d시간 %d분",hour,min)
        act_place_search_route_tv_traslate.text =String.format("환승 %d회",intent.getIntExtra("transitCount",0))
        act_place_search_route_tv_walk.text =String.format("도보 %d분",intent.getIntExtra("totalWalkTime",0))
        act_place_search_route_tv_money.text =String.format("%d원",intent.getIntExtra("totalPay",0))
        when(intent.getIntExtra("pathType",-1)){
            3 -> {
                act_place_search_route_tv_riding_kind.text = "지하철 + 버스"
            }
            1 -> {
                act_place_search_route_tv_riding_kind.text = "지하철"
            }
            2 -> {
                act_place_search_route_tv_riding_kind.text = "버스"
            }
        }
        act_route_et_engine_from.text = startPlaceName
        act_route_et_engine_to.text = endPlaceName
        routeRecyclerView = findViewById(R.id.act_route_rv_riding_info)
        routeAdapter = RouteAdapter(this, object : RouteViewHolder.ItemClickListener {
            override fun dropDownClick(position: Int) {
                when (routeAdapter.getClicked(position)) {
                    false -> {
                        Log.e("sdas", "Asd")
                    }
                    else -> {
                        Log.e("aaa", "Bbb")
                    }
                }
            }
        })
        routeRecyclerView.adapter = routeAdapter
        makeListItem()
        intent()
        setButton()

    }

    private fun makeListItem() {
        /*
        val callRoute: Call<RouteResponse> = EarlyBuddyServiceImpl.service.getRoute(
            ScheduleActivity.schedulePlace.startPlaceX,
            ScheduleActivity.schedulePlace.startPlaceY,
            ScheduleActivity.schedulePlace.endPlaceX,
            ScheduleActivity.schedulePlace.endPlaceY,
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
                    routeAdapter.setRouteItem(route.data.path[0].subPath)
                    routeAdapter.routeList[0].startName = ScheduleActivity.schedulePlace.startPlaceName
                    routeAdapter.notifyDataSetChanged()
                    routeAdapter.routeList[route.data.path[0].subPath.size-1].endName = ScheduleActivity.schedulePlace.endPlaceName
                }
            }
        })*/

        val subpath = ScheduleActivity.selectedPath.path!!.subPath
        routeAdapter.setRouteItem(subpath)
        routeAdapter.routeList[0].startName = ScheduleActivity.schedulePlace.startPlaceName
        routeAdapter.notifyDataSetChanged()
        routeAdapter.routeList[subpath.size-1].endName = ScheduleActivity.schedulePlace.endPlaceName
    }


    private fun intent() {
        act_route_iv_back.setOnClickListener {
            finish()
        }
    }

    private fun setButton(){
        act_route_cv_submit.setOnClickListener{
            Route.isSelected = true
            finish()

        }
    }
}
