package com.devaon.early_buddy_android.feature.home

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.schedule.GetScheduleData
import com.devaon.early_buddy_android.network.EarlyBuddyServiceImpl
import kotlinx.android.synthetic.main.activity_home_route.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRouteActivity : AppCompatActivity() {

    private lateinit var routeRecyclerView: RecyclerView
    private lateinit var routeAdapter: HomeRouteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_route)

        intent()
        routeRecyclerView = findViewById(R.id.act_home_route_rv)
        routeAdapter = HomeRouteAdapter(this, object : HomeRouteViewHolder.ItemClickListener {
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


    }

    private fun makeListItem() {
        val callRoute: Call<GetScheduleData> = EarlyBuddyServiceImpl.service.getHomeRoute(
            72
        )

//        {
//            "SX" : "127.08282465301149",
//            "SY" : "37.62072502768881",
//            "EX" : "127.03746391719882",
//            "EY" : "37.4720040276288",
//        }pathType

        callRoute.enqueue(object : Callback<GetScheduleData> {
            override fun onFailure(call: Call<GetScheduleData>, t: Throwable) {
                Log.e("error is ", t.toString())
            }

            override fun onResponse(
                call: Call<GetScheduleData>,
                response: Response<GetScheduleData>
            ) {
                if (response.isSuccessful) {
                    Log.e("result is ", response.body().toString())
                    val route = response.body()!!
                    routeAdapter.setRouteItem(route.data.pathInfo.subPath)
                    routeAdapter.routeList[0].detailStartAddress = String.format("출발지 : %s",route.data.scheduleInfo.startAddress)
                    act_home_route_riding_kind.text = String.format("%d원",route.data.pathInfo.totalPay)
                    act_home_route_tv_time.text = String.format("%d분",route.data.pathInfo.totalTime)
                    routeAdapter.notifyDataSetChanged()
                }
            }
        })
    }

    private fun intent() {
        act_home_route_iv_back.setOnClickListener {
            finish()
        }
        act_home_route_tv_promise.text = intent.getStringExtra("scheduleName")
        act_home_route_tv_time_value.text = intent.getStringExtra("scheduleStartTime")
        act_home_route_tv_location_value.text = intent.getStringExtra("endAddress")
    }
}
